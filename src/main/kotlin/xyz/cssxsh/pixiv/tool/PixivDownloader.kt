package xyz.cssxsh.pixiv.tool

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import okhttp3.ConnectionPool
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.exception.*
import java.net.*
import java.util.concurrent.*

open class PixivDownloader(
    async: Int = 32,
    protected open val blockSize: Int = 512 * HTTP_KILO, // HTTP use 1022 no 1024,
    protected open val proxy: Proxy? = null,
    protected open val doh: String = JAPAN_DNS,
    protected open val host: Map<String, List<String>> = DEFAULT_PIXIV_HOST,
) {

    protected open val timeout: Long = 10 * 1000L

    protected open val ignore: suspend (Throwable) -> Boolean = {
        it is IOException || it is HttpRequestTimeoutException
    }

    protected open val channel = Channel<Int>(async)

    protected open fun client() = HttpClient(OkHttp) {
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        install(HttpTimeout) {
            socketTimeoutMillis = timeout
            connectTimeoutMillis = timeout
            requestTimeoutMillis = null
        }
        defaultRequest {
            header(HttpHeaders.CacheControl, "no-cache")
            header(HttpHeaders.Connection, "keep-alive")
            header(HttpHeaders.Pragma, "no-cache")
        }
        engine {
            config {
                connectionPool(ConnectionPool(5, 10, TimeUnit.MINUTES))
                sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                hostnameVerifier { _, _ -> true }
                proxy(this@PixivDownloader.proxy)
                dns(RubyDns(doh, host))
            }
        }
    }

    protected open val clients by lazy { MutableList(8) { client() } }

    private suspend fun <T> withHttpClient(client: HttpClient, block: suspend HttpClient.() -> T): T = supervisorScope {
        while (isActive) {
            channel.send(clients.indexOf(client))
            return@supervisorScope try {
                with(client) { block() }
            } catch (throwable: Throwable) {
                if (isActive && ignore(throwable)) {
                    null
                } else {
                    throw throwable
                }
            } finally {
                channel.receive()
            } ?: continue
        }
        throw CancellationException(null, null)
    }

    private suspend fun length(client: HttpClient, url: Url): Int = withHttpClient(client) {
        val response = head<HttpResponse>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
            header(HttpHeaders.Range, "bytes=0-")
            header(HttpHeaders.CacheControl, "no-store")
            header(HttpHeaders.Connection, "keep-alive")
            header(HttpHeaders.Pragma, "no-store")
        }
        if (response.headers[HttpHeaders.Age]?.toIntOrNull() == 0) {
            throw NoCacheException(response)
        }
        response.headers[HttpHeaders.ContentLength]?.toIntOrNull()
            ?: response.headers[HttpHeaders.ContentRange]?.substringAfter('/')?.toInt()
            ?: throw MatchContentLengthException(response)
    }

    private suspend fun range(client: HttpClient, url: Url, dst: ByteArray, offset: Int) = withHttpClient(client) {
        val length = minOf(dst.size - offset, blockSize)
        val range = "bytes=${offset}-${offset + length - 1}"

        val response = get<HttpResponse>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
            header(HttpHeaders.Range, range)
            url {
                fragment = range
            }
        }

        if (response.headers[HttpHeaders.Age]?.toIntOrNull() == 0) {
            throw NoCacheException(response)
        }

        if ((response.headers[HttpHeaders.ContentLength]?.toIntOrNull() ?: -1) != length) {
            throw MatchContentLengthException(response)
        }

        response.content.readFully(dst, offset, length)
    }

    private suspend fun all(client: HttpClient, url: Url, dst: ByteArray) = withHttpClient(client) {
        val response = get<HttpResponse>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
        }

        if (response.headers[HttpHeaders.Age]?.toIntOrNull() == 0) {
            throw NoCacheException(response)
        }

        if ((response.headers[HttpHeaders.ContentLength]?.toIntOrNull() ?: -1) != dst.size) {
            throw MatchContentLengthException(response)
        }

        response.content.readFully(dst, 0, dst.size)
    }

    private suspend fun downloadRangesOrAll(client: HttpClient, url: Url, length: Int): ByteArray = supervisorScope {
        val bytes = ByteArray(size = length)
        if (blockSize <= 0 || length <= blockSize) {
            all(client = client, url = url, dst = bytes)
        } else {
            (0 until length step blockSize).map { offset ->
                async(Dispatchers.IO) {
                    range(
                        client = client,
                        url = url,
                        dst = bytes,
                        offset = offset
                    )
                }
            }.awaitAll()
        }
        bytes
    }

    open suspend fun download(url: Url): ByteArray = supervisorScope {
        var client = clients.random()
        var length = 0
        while (isActive && length == 0) {
            try {
                length = length(client = client, url = url)
            } catch (_: MatchContentLengthException) {
                client = clients.random()
            } catch (_: NoCacheException) {
                client = clients.random()
            }
        }

        downloadRangesOrAll(client = client, url = url, length = length)
    }

    open suspend fun <R> downloadImageUrls(
        urls: List<Url>,
        block: suspend (url: Url, deferred: Deferred<ByteArray>) -> R,
    ): List<R> = supervisorScope {
        urls.map { url ->
            url to async { download(url) }
        }.map { (url, deferred) ->
            block(url, deferred)
        }
    }
}
package xyz.cssxsh.pixiv.tool

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import okio.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.exception.*
import java.net.*

open class PixivDownloader(
    async: Int = 32,
    private val blockSize: Int = 512 * HTTP_KILO, // HTTP use 1022 no 1024,
    private val proxy: Proxy? = null,
    private val doh: String = JAPAN_DNS,
    private val host: Map<String, List<String>> = DEFAULT_PIXIV_HOST,
) {

    protected open val timeout: Long = 10 * 1000L

    protected open val ignore: suspend (Throwable) -> Boolean = {
        (it is IOException && it !is MatchContentLengthException) || it is HttpRequestTimeoutException
    }

    private val channel = Channel<Int>(async)

    protected open fun client() = HttpClient(OkHttp) {
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        install(HttpTimeout) {
            socketTimeoutMillis = timeout
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
        }
        defaultRequest {
            header(HttpHeaders.CacheControl, "no-cache")
            header(HttpHeaders.Connection, "keep-alive")
            header(HttpHeaders.Pragma, "no-cache")
        }
        engine {
            config {
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
            runCatching {
                client.run { block() }
            }.also {
                channel.receive()
            }.onSuccess {
                return@supervisorScope it
            }.onFailure { throwable ->
                if (isActive && ignore(throwable)) {
                    //
                } else {
                    throw throwable
                }
            }
        }
        throw CancellationException()
    }

    private suspend fun <T, R> Iterable<T>.asyncMapIndexed(transform: suspend (Int, T) -> R) = supervisorScope {
        mapIndexed { index, value ->
            async {
                transform(index, value)
            }
        }.awaitAll()
    }

    private fun IntRange.getLength() = (last - first + 1)

    private fun IntRange.getHeader() = "bytes=${first}-${last}"

    private suspend fun length(client: HttpClient, url: Url): Int = withHttpClient(client) {
        val response = head<HttpResponse>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
            header(HttpHeaders.Range, "bytes=0-")
            header(HttpHeaders.CacheControl, "no-store")
            header(HttpHeaders.Connection, "keep-alive")
            header(HttpHeaders.Pragma, "no-store")
        }
        response.headers[HttpHeaders.ContentLength]?.toInt()
            ?: response.headers[HttpHeaders.ContentRange]?.substringAfter('/')?.toInt()
            ?: throw MatchContentLengthException(response)
    }

    private fun ByteArray.check(expected: Int) = also {
        if (it.size != expected) throw ByteArrayException("Expected ${expected}, actual ${it.size}")
    }

    private suspend fun downloadRange(client: HttpClient, url: Url, range: IntRange) = withHttpClient(client) {
        get<ByteArray>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
            header(HttpHeaders.Range, range.getHeader())
        }.check(range.getLength())
    }

    private suspend fun downloadAll(client: HttpClient, url: Url) = withHttpClient(client) {
        get<ByteArray>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
        }
    }

    private suspend fun downloadRangesOrAll(client: HttpClient, url: Url, length: Int): ByteArray {
        return if (length < blockSize) {
            downloadAll(client = client, url = url)
        } else {
            (0 until length step blockSize).asyncMapIndexed { _, offset ->
                downloadRange(
                    client = client,
                    url = url,
                    range = offset until (offset + blockSize).coerceAtMost(length)
                )
            }.reduce { accumulator, bytes -> accumulator + bytes }
        }.check(length)
    }

    open suspend fun download(url: Url): ByteArray = supervisorScope {
        var client = clients.random()
        var length = 0
        while (isActive && length == 0) {
            runCatching {
                length(client = client, url = url)
            }.onSuccess {
                length = it
            }.onFailure {
                if (it !is MatchContentLengthException) {
                    throw it
                } else {
                    client = clients.random()
                }
            }
        }

        downloadRangesOrAll(client = client, url = url, length = length)
    }

    open suspend fun <R> downloadImageUrls(
        urls: List<Url>,
        block: (url: Url, result: Result<ByteArray>) -> R,
    ): List<R> = urls.asyncMapIndexed { _, url ->
        block(url, runCatching { download(url) })
    }
}
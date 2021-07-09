package xyz.cssxsh.pixiv.tool

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import okhttp3.OkHttpClient
import okio.IOException
import xyz.cssxsh.pixiv.*
import java.net.*
import java.util.logging.Level
import java.util.logging.Logger

open class PixivDownloader(
    async: Int = 32,
    private val blockSize: Int = 512 * HTTP_KILO, // HTTP use 1022 no 1024,
    private val timeout: Long = 10 * 1000L,
    private val proxy: Proxy? = null,
    private val doh: String = JAPAN_DNS,
    private val host: Map<String, List<String>> = DEFAULT_PIXIV_HOST,
) {

    protected open val ignore: suspend (Throwable) -> Boolean = {
        it is IOException || it is HttpRequestTimeoutException
    }

    private val channel = Channel<Int>(async)

    init {
        Logger.getLogger(OkHttpClient::class.java.name).level = Level.OFF
    }

    protected open val client = HttpClient(OkHttp) {
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
        engine {
            config {
                sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                hostnameVerifier { _, _ -> true }
                proxy(this@PixivDownloader.proxy)
                dns(RubyDns(doh, host))
            }
        }
    }

    private suspend fun <T> withHttpClient(block: suspend HttpClient.() -> T): T = supervisorScope {
        while (isActive) {
            channel.send(0)
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

    private suspend fun <T, R> Iterable<T>.asyncMapIndexed(
        transform: suspend (Int, T) -> R,
    ): List<R> = withContext(Dispatchers.IO) {
        mapIndexed { index, value ->
            async {
                transform(index, value)
            }
        }.awaitAll()
    }

    private fun IntRange.getLength() = (last - first + 1)

    private fun IntRange.getHeader() = "bytes=${first}-${last}"

    private suspend fun length(url: Url): Int = withHttpClient {
        head<HttpMessage>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
        }.headers[HttpHeaders.ContentLength]?.toInt() ?: throw IOException("Not Match ContentLength")
    }

    private fun ByteArray.check(expected: Int) = also {
        check(it.size == expected) { "Expected ${expected}, actual ${it.size}" }
    }

    private suspend fun downloadRange(url: Url, range: IntRange): ByteArray = withHttpClient {
        get<ByteArray>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
            header(HttpHeaders.Range, range.getHeader())
        }.check(range.getLength())
    }

    private suspend fun downloadAll(url: Url): ByteArray = withHttpClient {
        get(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
        }
    }

    private suspend fun downloadRangesOrAll(url: Url, length: Int): ByteArray {
        return if (length < blockSize) {
            downloadAll(url = url)
        } else {
            (0 until length step blockSize).asyncMapIndexed { _, offset ->
                downloadRange(
                    url = url,
                    range = offset until (offset + blockSize).coerceAtMost(length)
                )
            }.reduce { accumulator, bytes -> accumulator + bytes }
        }.check(length)
    }

    private suspend fun download(url: Url): ByteArray = downloadRangesOrAll(url = url, length = length(url = url))

    suspend fun <R> downloadImageUrls(
        urls: List<Url>,
        block: (url: Url, result: Result<ByteArray>) -> R,
    ): List<R> = urls.asyncMapIndexed { _, url ->
        block(url, runCatching { download(url) })
    }
}
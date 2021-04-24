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
import xyz.cssxsh.pixiv.HTTP_KILO
import xyz.cssxsh.pixiv.JAPAN_DNS
import xyz.cssxsh.pixiv.toProxy
import java.io.IOException
import java.net.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.time.Duration
import kotlin.time.seconds

open class PixivDownloader(
    private val async: Int,
    private val blockSize: Int,
    private val socketTimeout: Long,
    private val connectTimeout: Long,
    private val requestTimeout: Long,
    private val proxySelector: ProxySelector?,
    private val dns: LocalDns,
    private val ignore: suspend (Throwable) -> Boolean,
    private val head: Boolean,
) {

    private val channel = Channel<Int>(async)

    constructor(
        async: Int = 32,
        kilobytes: Int = 512,
        timeout: Duration = (10).seconds,
        proxyUrl: String? = null,
        dns: String = JAPAN_DNS,
        initHost: Map<String, List<String>> = emptyMap(),
        cname: Map<String, String> = emptyMap(),
        ignore: suspend (Throwable) -> Boolean = { false },
        head: Boolean = true,
    ) : this(
        async = async,
        blockSize = kilobytes * HTTP_KILO, // HTTP use 1022 no 1024
        socketTimeout = timeout.toLongMilliseconds(),
        connectTimeout = timeout.toLongMilliseconds(),
        requestTimeout = timeout.toLongMilliseconds(),
        proxySelector = proxyUrl?.toProxy()?.let { proxy ->
            object : ProxySelector() {
                override fun select(uri: URI?) = mutableListOf<Proxy>().apply {
                    if (uri?.host !in cname) add(proxy)
                }

                override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {
                    // println("connectFailed； $uri")
                }
            }
        },
        dns = LocalDns(
            dns = dns,
            initHost = initHost,
            cname = cname
        ),
        ignore = ignore,
        head = head
    )

    init {
        Logger.getLogger(OkHttpClient::class.java.name).level = Level.OFF
    }

    private val client
        get() = HttpClient(OkHttp) {
            ContentEncoding {
                gzip()
                deflate()
                identity()
            }
            install(HttpTimeout) {
                socketTimeoutMillis = socketTimeout
                connectTimeoutMillis = connectTimeout
                requestTimeoutMillis = requestTimeout
            }
            engine {
                config {
                    sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                    hostnameVerifier { _, _ -> true }
                    proxySelector?.let {
                        proxySelector(it)
                    }
                    dns(dns)
                }
            }
        }

    private suspend fun <T> withHttpClient(block: suspend HttpClient.() -> T): T = withContext(Dispatchers.IO) {
        while (isActive) {
            channel.send(0)
            runCatching {
                client.use { it.block() }
            }.also {
                channel.receive()
            }.onSuccess {
                return@withContext it
            }.onFailure { throwable ->
                if (isActive && ignore(throwable)) {
                    //
                } else {
                    throw throwable
                }
            }
        }
        TODO()
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

    private suspend fun getSize(url: Url): Int = withHttpClient {
        get<HttpMessage>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
            header(HttpHeaders.Range, (0..0).getHeader())
        }.headers[HttpHeaders.ContentRange]!!.substringAfterLast("/").toInt()
    }

    private suspend fun headSize(url: Url): Int = withHttpClient {
        head<HttpMessage>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
        }.headers[HttpHeaders.ContentLength]!!.toInt()
    }

    private suspend fun downloadRange(url: Url, range: IntRange): ByteArray = withHttpClient {
        get<ByteArray>(url) {
            header(HttpHeaders.Host, url.host)
            header(HttpHeaders.Referrer, url)
            header(HttpHeaders.Range, range.getHeader())
        }.also {
            check(it.size == range.getLength()) {
                "Expected ${range.getLength()}, actual ${it.size}"
            }
        }
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
        }
    }

    private suspend fun download(url: Url): ByteArray = downloadRangesOrAll(
        url = url,
        length = if (head) headSize(url = url) else getSize(url = url)
    )

    suspend fun <R> downloadImageUrls(
        urls: List<Url>,
        block: (url: Url, result: Result<ByteArray>) -> R,
    ): List<R> = urls.asyncMapIndexed { _, url ->
        runCatching {
            download(url = url)
        }.let { result ->
            block(url, result)
        }
    }
}
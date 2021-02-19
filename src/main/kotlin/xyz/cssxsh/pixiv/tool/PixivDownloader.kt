package xyz.cssxsh.pixiv.tool

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.Referrer
import io.ktor.http.HttpHeaders.Range
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.io.IOException
import java.net.*

open class PixivDownloader(
    private val maxUrlAsyncNum: Int,
    private val blockSize: Int,
    private val socketTimeout: Long,
    private val connectTimeout: Long,
    private val requestTimeout: Long,
    private val proxySelector: ProxySelector?,
    private val dns: LocalDns,
    private val ignore: (String, Throwable, String) -> Boolean,
) {

    private val urlChannel = Channel<String>(maxUrlAsyncNum)

    constructor(
        maxUrlAsyncNum: Int = 64,
        blockSize: Int = 256 * 1024,
        socketTimeout: Long = 30_000,
        connectTimeout: Long = 30_000,
        requestTimeout: Long = 30_000,
        proxyUrl: String? = null,
        dns: String = "https://public.dns.iij.jp/dns-query",
        initHost: Map<String, List<String>> = emptyMap(),
        cname: Map<String, String> = emptyMap(),
        ignore: (String, Throwable, String) -> Boolean = { _, _, _ -> false },
    ) : this(
        maxUrlAsyncNum = maxUrlAsyncNum,
        blockSize = blockSize,
        socketTimeout = socketTimeout,
        connectTimeout = connectTimeout,
        requestTimeout = requestTimeout,
        proxySelector = proxyUrl?.toProxyConfig()?.let { proxy ->
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
    )

    private fun httpClient(): HttpClient = HttpClient(OkHttp) {
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

    private suspend fun <T, R> Iterable<T>.asyncMapIndexed(
        transform: suspend (Int, T) -> R,
    ): List<R> = withContext(Dispatchers.IO) {
        mapIndexed { index, value ->
            async {
                transform(index, value)
            }
        }.awaitAll()
    }

    private suspend fun HttpClient.getSizeIgnoreException(url: String): Int {
        var size: Int? = null
        while (isActive && size == null) {
            size = runCatching {
                head<HttpMessage>(url) { headers[Referrer] = url }.contentLength()
            }.onFailure { throwable ->
                if ((isActive && ignore(url, throwable, "GET_SIZE"))) {
                    //
                } else {
                    throw throwable
                }
            }.getOrNull()?.toInt()
        }
        return size!!
    }

    private fun IntRange.getLength() = (last - first + 1)

    private fun IntRange.getHeader() = "bytes=${first}-${last}"

    private suspend fun HttpClient.downloadRangeIgnoreException(url: String, range: IntRange): ByteArray {
        var bytes: ByteArray? = null
        while (isActive && bytes == null) {
            bytes = runCatching {
                get<ByteArray>(url) {
                    header(Referrer, url)
                    header(Range, range.getHeader())
                }.also {
                    check(it.size == range.getLength()) {
                        "Expected ${range.getLength()}, actual ${it.size}"
                    }
                }
            }.onFailure { throwable ->
                if (isActive && ignore(url, throwable, range.getHeader())) {
                    //
                } else {
                    throw throwable
                }
            }.getOrNull()
        }
        return bytes!!
    }

    private suspend fun HttpClient.downloadAllIgnoreException(url: String): ByteArray {
        var bytes: ByteArray? = null
        while (isActive && bytes == null) {
            bytes = runCatching {
                get<ByteArray>(url) {
                    header(Referrer, url)
                }
            }.onFailure { throwable ->
                if (isActive && ignore(url, throwable, "ALL")) {
                    //
                } else {
                    throw throwable
                }
            }.getOrNull()
        }
        return bytes!!
    }

    private suspend fun HttpClient.downloadRangesOrAll(url: String, length: Int): ByteArray {
        return if (length < blockSize) {
            downloadAllIgnoreException(url = url)
        } else {
            (0 until length step blockSize).asyncMapIndexed { _, offset ->
                downloadRangeIgnoreException(
                    url = url,
                    range = offset until (offset + blockSize).coerceAtMost(length)
                )
            }.reduce { accumulator, bytes -> accumulator + bytes }
        }
    }

    private suspend fun HttpClient.download(url: String): ByteArray = downloadRangesOrAll(
        url = url,
        length = getSizeIgnoreException(url = url)
    )

    suspend fun <R> downloadImageUrls(
        urls: List<String>,
        block: (url: String, result: Result<ByteArray>) -> R,
    ): List<R> = urls.asyncMapIndexed { _, url ->
        urlChannel.send(url)
        httpClient().use { client ->
            client.runCatching {
                download(url = url)
            }
        }.let { result ->
            urlChannel.receive()
            block(url, result)
        }
    }
}
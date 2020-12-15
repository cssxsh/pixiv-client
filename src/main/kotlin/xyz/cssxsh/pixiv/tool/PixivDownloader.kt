package xyz.cssxsh.pixiv.tool

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.Referrer
import io.ktor.http.HttpHeaders.Range
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import xyz.cssxsh.pixiv.client.Tool
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import java.net.InetAddress

open class PixivDownloader(
    maxAsyncNum: Int = 16,
    var blockSize: Int = 1024 * 1024,
    var socketTimeout: Long = 15_000,
    var connectTimeout: Long = 15_000,
    var requestTimeout: Long = 60_000,
    var proxyUrl: String? = null,
    var dnsUrl: String = "https://public.dns.iij.jp/dns-query",
    var cname: Map<String, String> = emptyMap(),
    host: Map<String, List<String>> = emptyMap(),
    var ignore: (String, Throwable, String) -> Boolean = { _, _, _ -> false },
) {

    private val host: MutableMap<String, List<InetAddress>> = host.mapValues { (_, ips) ->
        ips.map { InetAddress.getByName(it) }
    }.toMutableMap()

    private fun httpClient(): HttpClient = HttpClient(OkHttp) {
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        install(HttpTimeout) {
            socketTimeoutMillis = socketTimeout
            connectTimeoutMillis = connectTimeout
            connectTimeoutMillis = requestTimeout
        }
        engine {
            config {
                Tool.getProxyByUrl(proxyUrl)?.let {
                    proxy(it)
                }
                dns(LocalDns(
                    dnsUrl = dnsUrl.toHttpUrlOrNull(),
                    host = host,
                    cname = cname
                ))
            }
        }
    }


    private suspend fun HttpClient.getSizeIgnoreException(url: String): Int = runCatching {
        head<HttpResponse>(url) { headers[Referrer] = url }.contentLength()!!.toInt()
    }.getOrElse { throwable ->
        if (ignore(url, throwable, "get_size")) {
            getSizeIgnoreException(url)
        } else {
            throw throwable
        }
    }

    private suspend fun HttpClient.downloadIgnoreException(
        url: String,
        range: IntRange,
    ): ByteArray = runCatching {
        get<HttpResponse>(url) {
            header(Referrer, url)
            header(Range, "bytes=${range.first}-${range.last}")
            timeout {
                requestTimeoutMillis = maxOf((range.last - range.first + 1).toLong(), requestTimeout)
            }
        }.readBytes((range.last - range.first + 1))
    }.getOrElse { throwable ->
        if (ignore(url, throwable, "bytes=${range.first}-${range.last}")) {
            downloadIgnoreException(url = url, range = range)
        } else {
            throw throwable
        }
    }

    private val channel = Channel<String>(maxAsyncNum)

    suspend fun <R> downloadImageUrls(
        urls: List<String>,
        block: (index: Int, url: String, result: Result<ByteArray>) -> R,
    ): List<R> = withContext(Dispatchers.IO) {
        urls.mapIndexed { index, url ->
            async {
                httpClient().use { client ->
                    runCatching {
                        val length = client.getSizeIgnoreException(url = url)
                        (0 until length step blockSize).map { offset ->
                            async {
                                channel.send(url)
                                kotlin.runCatching {
                                    client.downloadIgnoreException(
                                        url = url,
                                        range = offset until minOf(offset + blockSize, length)
                                    )
                                }.also {
                                    channel.receive()
                                }.getOrThrow()
                            }
                        }.awaitAll().reduce { acc, bytes -> acc + bytes }
                    }.let { result ->
                        block(index, url, result)
                    }
                }
            }
        }.awaitAll()
    }
}
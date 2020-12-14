package xyz.cssxsh.pixiv.tool

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.IllustInfo
import kotlin.io.use

suspend inline fun <R> PixivClient.downloadImages(
    illust: IllustInfo,
    crossinline block: PixivClient.(Int, String, Result<ByteArray>) -> R
): List<R> = downloadImageUrls(
    urls = illust.getOriginUrl(),
    block = block
)

suspend inline fun <R> PixivClient.downloadImages(
    illust: IllustInfo,
    predicate: (type: String, url: String) -> Boolean,
    crossinline block: PixivClient.(Int, String, Result<ByteArray>) -> R
): List<R> = illust.getImageUrls().flatMap { fileUrls ->
    fileUrls.filter { predicate(it.key, it.value) }.values
}.let {
    downloadImageUrls(urls = it, block = block)
}

suspend fun HttpClient.downloadIgnoreException(
    url: String,
    socketTimeout: Long,
    connectTimeout: Long,
    requestTimeout: Long,
    ignore: (String, Throwable) -> Boolean
): ByteArray = runCatching {
    get<ByteArray>(url) {
        headers["Referer"] = url
        timeout {
            socketTimeoutMillis = socketTimeout
            connectTimeoutMillis = connectTimeout
            requestTimeoutMillis = requestTimeout
        }
    }
}.getOrElse { throwable ->
    if (ignore(url, throwable)) {
        downloadIgnoreException(
            url = url,
            socketTimeout = socketTimeout,
            connectTimeout = connectTimeout,
            requestTimeout = requestTimeout,
            ignore = ignore
        )
    } else {
        throw throwable
    }
}

suspend inline fun <R> PixivClient.downloadImageUrls(
    urls: Iterable<String>,
    maxAsyncNum: Int = 8,
    socketTimeout: Long = 10_000,
    connectTimeout: Long = 10_000,
    requestTimeout: Long = 600_000,
    noinline ignore: (String, Throwable) -> Boolean = { _, _ -> false },
    crossinline block: PixivClient.(Int, String, Result<ByteArray>) -> R
): List<R> = httpClient().use { client ->
    val channel = Channel<String>(maxAsyncNum)
    urls.mapIndexed { index, url ->
        (this@downloadImageUrls).async {
            channel.send(url)
            runCatching {
                client.downloadIgnoreException(
                    url = url,
                    socketTimeout = socketTimeout,
                    connectTimeout = connectTimeout,
                    requestTimeout = requestTimeout,
                    ignore = ignore
                )
            }.also {
                channel.receive()
            }.let { content ->
                block(index, url, content)
            }
        }
    }.awaitAll()
}


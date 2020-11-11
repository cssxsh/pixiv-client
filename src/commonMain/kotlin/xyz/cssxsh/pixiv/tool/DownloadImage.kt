package xyz.cssxsh.pixiv.tool

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.IllustInfo
import xyz.cssxsh.pixiv.useHttpClient

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
    requestTimeout: Long,
    ignore: (String, Throwable) -> Boolean
): ByteArray = runCatching {
    get<ByteArray>(url) {
        headers["Referer"] = url
        timeout {
            socketTimeoutMillis = 10_000
            connectTimeoutMillis = 10_000
            requestTimeoutMillis = requestTimeout
        }
    }
}.onFailure { if (ignore(url, it).not()) throw it }.getOrElse { downloadIgnoreException(url, requestTimeout, ignore) }

suspend inline fun <R> PixivClient.downloadImageUrls(
    urls: Iterable<String>,
    maxAsyncNum: Int = 8,
    requestTimeout: Long = 300_000,
    noinline ignore: (String, Throwable) -> Boolean = { _, _ -> false },
    crossinline block: PixivClient.(Int, String, Result<ByteArray>) -> R
): List<R> = useHttpClient { client ->
    val channel = Channel<String>(maxAsyncNum)
    urls.mapIndexed { index, url ->
        async {
            channel.send(url)
            runCatching {
                client.downloadIgnoreException(url, requestTimeout, ignore)
            }.also {
                channel.receive()
            }.let { content ->
                block(index, url, content)
            }
        }
    }.awaitAll()
}


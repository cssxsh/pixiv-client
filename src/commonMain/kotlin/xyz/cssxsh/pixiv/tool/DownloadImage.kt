package xyz.cssxsh.pixiv.tool

import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.IllustInfo
import xyz.cssxsh.pixiv.useHttpClient

suspend inline fun <reified T, R> PixivClient.downloadImage(
    illust: IllustInfo,
    crossinline block:  PixivClient.(Int, String, Result<T>) -> R
): List<R> = downloadImageUrl(
    urls = illust.getOriginUrl(),
    block = block
)

suspend inline fun <reified T, R> PixivClient.downloadImage(
    illust: IllustInfo,
    predicate: (type: String, url: String) -> Boolean,
    crossinline block:  PixivClient.(Int, String, Result<T>) -> R
): List<R> = illust.getImageUrls().flatMap { fileUrls ->
    fileUrls.filter { predicate(it.key, it.value) }.values
}.let {
    downloadImageUrl(urls = it, block = block)
}

suspend inline fun <reified T, R>  PixivClient.downloadImageUrl(
    urls: List<String>,
    maxAsyncNum: Int = 8,
    crossinline block:  PixivClient.(Int, String, Result<T>) -> R
): List<R> = useHttpClient { client ->
    val channel = Channel<String>(maxAsyncNum)
    urls.mapIndexed { index, url ->
        async {
            channel.send(url)
            runCatching {
                client.get<T>(url) {
                    headers["Referer"] = url
                    timeout {
                        socketTimeoutMillis = 30_000
                        connectTimeoutMillis = 30_000
                        requestTimeoutMillis = 300_000
                    }
                }
            }.also {
                channel.receive()
            }.let { content ->
                block(index, url, content)
            }
        }
    }.awaitAll()
}


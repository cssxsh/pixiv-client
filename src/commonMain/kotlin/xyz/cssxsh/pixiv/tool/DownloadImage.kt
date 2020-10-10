package xyz.cssxsh.pixiv.tool

import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.IllustInfo
import xyz.cssxsh.pixiv.useHttpClient

suspend inline fun <reified T> PixivClient.downloadImage(
    illust: IllustInfo,
    crossinline block:  PixivClient.(T) -> Unit = {}
): List<Result<T>> = downloadImageUrl(list = illust.getOriginUrl(), block = block)

suspend inline fun <reified T> PixivClient.downloadImage(
    illust: IllustInfo,
    predicate: (name: String, url: String) -> Boolean,
    crossinline block:  PixivClient.(T) -> Unit = {}
): List<Result<T>> = illust.getImageUrls().flatMap { fileUrls ->
    fileUrls.filter { predicate(it.key, it.value) }.values
}.let {
    downloadImageUrl(list = it, block = block)
}

suspend inline fun <reified T>  PixivClient.downloadImageUrl(
    list: List<String>,
    maxAsyncNum: Int = 8,
    crossinline block:  PixivClient.(T) -> Unit
): List<Result<T>> = list.let {
    val channel = Channel<String>(maxAsyncNum)
    it.map { url ->
        (this).async {
            channel.send(url)
            runCatching {
                useHttpClient { client ->
                    client.get<T>(url) {
                        headers["Referer"] = url
                        timeout {
                            socketTimeoutMillis = 30_000
                            connectTimeoutMillis = 30_000
                            requestTimeoutMillis = 300_000
                        }
                    }
                }.also { content ->
                    block(content)
                }
            }.also {
                channel.receive()
            }
        }
    }.awaitAll()
}


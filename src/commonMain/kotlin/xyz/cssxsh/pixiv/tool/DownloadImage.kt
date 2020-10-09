package xyz.cssxsh.pixiv.tool

import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.IllustInfo

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
    max: Int = 8,
    crossinline block:  PixivClient.(T) -> Unit
): List<Result<T>> = list.let {
    var count = 0
    it.map { url ->
        (this).async {
            if (count < max) count++
            runCatching {
                httpClient.get<T>(url) {
                    headers["Referer"] = url
                    timeout {
                        socketTimeoutMillis = 30_000
                        connectTimeoutMillis = 30_000
                        requestTimeoutMillis = 300_000
                    }
                }.also { content ->
                    block(content)
                }
            }
            count--
        }
    }
    awaitAll()
}


package xyz.cssxsh.pixiv.tool

import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.IllustInfo

suspend inline fun <reified T> PixivClient.downloadImage(
    illust: IllustInfo,
    crossinline block:  PixivClient.(T) -> Unit = {}
): List<Result<T>> = illust.getOriginUrl().map { url ->
    (this).async {
        runCatching {
            httpClient.get<T>(url) {
                headers["Referer"] = url
                timeout {
                    socketTimeoutMillis = 30_000
                    connectTimeoutMillis = 30_000
                    requestTimeoutMillis = 300_000
                }
            }
        }.onSuccess {
            block(it)
        }
    }
}.run {
    awaitAll()
}

suspend inline fun <reified T> PixivClient.downloadImage(
    illust: IllustInfo,
    predicate: (name: String, url: String) -> Boolean,
    crossinline block:  PixivClient.(T) -> Unit = {}
): List<Result<T>> = illust.getImageUrls().flatMap { fileUrls ->
    fileUrls.filter { predicate(it.key, it.value) }.values
}.map { url ->
    (this).async {
        runCatching {
            httpClient.get<T>(url) {
                headers["Referer"] = url
                timeout {
                    socketTimeoutMillis = 30_000
                    connectTimeoutMillis = 30_000
                    requestTimeoutMillis = 300_000
                }
            }
        }.onSuccess {
            block(it)
        }
    }
}.run {
    awaitAll()
}


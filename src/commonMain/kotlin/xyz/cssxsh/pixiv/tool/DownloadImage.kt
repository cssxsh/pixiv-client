package xyz.cssxsh.pixiv.tool

import io.ktor.client.request.*
import kotlinx.coroutines.*
import xyz.cssxsh.pixiv.api.app.AppApiUrls
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.IllustInfo

suspend inline fun <reified T> PixivClient.downloadImage(
    predicate: (name: String, url: String) -> Boolean = { name, _ -> "origin" in name },
    referer: String = AppApiUrls.rootPath,
    block: PixivClient.() -> IllustInfo
): List<Result<T>> = downloadImage(
    illust = run(block),
    predicate = predicate,
    referer = referer
)

suspend inline fun <reified T> PixivClient.downloadImage(
    illust: IllustInfo,
    predicate: (name: String, url: String) -> Boolean = { name, _ -> "origin" in name },
    referer: String = AppApiUrls.rootPath
): List<Result<T>> = illust.getImageUrls().flatMap { fileUrls ->
    fileUrls.filter { predicate(it.key, it.value) }.values
}.map { url ->
    (this).async {
        runCatching {
            httpClient.get<T>(url) { headers["Referer"] = referer }
        }
    }
}.run {
    awaitAll()
}


package xyz.cssxsh.pixiv.download

import io.ktor.client.request.*
import kotlinx.coroutines.*
import xyz.cssxsh.pixiv.api.app.AppApiUrls
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.IllustInfo
import kotlin.coroutines.CoroutineContext

suspend inline fun <reified T> PixivClient.downloadImage(
    illust: IllustInfo,
    regex: Regex,
    referer: String = AppApiUrls.rootPath,
    context: CoroutineContext = Dispatchers.Default
): List<Result<T>> = downloadImage(
    illust = illust,
    predicate = { key -> regex in key },
    referer = referer,
    context = context
)

suspend inline fun <reified T> PixivClient.downloadImage(
    illust: IllustInfo,
    predicate: (String) -> Boolean,
    referer: String = AppApiUrls.rootPath,
    context: CoroutineContext = Dispatchers.Default
): List<Result<T>> = withContext(context) {
    illust.getImageUrls().flatMap {
        it.filterKeys(predicate).values
    }.map {
        async {
            runCatching {
                httpClient.get<T>(it) { headers["Referer"] = referer }
            }
        }
    }.run {
        awaitAll()
    }
}
package xyz.cssxsh.pixiv.api.public

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.*

suspend fun PixivClient.postViewedWorks(
    pids:  List<Long>,
    url: String = PublicApi.ME_VIEWED_WORKS,
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): JsonElement = useHttpClient(ignore) { client ->
    client.post(url) {
        header(HttpHeaders.Referrer, PublicApi.REFERER)

        parameter("ids", pids.joinToString(","))
    }
}
package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient

suspend fun PixivClient.postViewedWorks(
    pids:  List<Long>,
    url: String = ME_VIEWED_WORKS,
): JsonElement = useHttpClient { client ->
    client.post(url) {
        header(HttpHeaders.Referrer, REFERER)

        parameter("ids", pids.joinToString(","))
    }
}
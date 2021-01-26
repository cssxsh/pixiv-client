package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.*

suspend fun PixivClient.postViewedWorks(
    pids:  List<Long>,
    url: String = PublicApi.ME_VIEWED_WORKS,
): JsonElement = useHttpClient { client ->
    client.post(url) {
        header(HttpHeaders.Referrer, PublicApi.REFERER)

        parameter("ids", pids.joinToString(","))
    }
}
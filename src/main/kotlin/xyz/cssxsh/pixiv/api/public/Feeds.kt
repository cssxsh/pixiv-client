package xyz.cssxsh.pixiv.api.public

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.useHttpClient

suspend fun PixivClient.getFeeds(
    type: String = "touch_nottext",
    relation: String = "all",
    showR18: Boolean = false,
    url: String = PublicApi.ME_FEEDS
): JsonElement = useHttpClient { client ->
    client.get(url) {
        header(HttpHeaders.Referrer, PublicApi.REFERER)

        parameter("type", type)
        parameter("relation", relation)
        parameter("show_r18", showR18)
    }
}
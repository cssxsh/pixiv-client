package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.PixivAppClient


suspend fun PixivAppClient.getFeeds(
    type: String = "touch_nottext",
    relation: String = "all",
    showR18: Boolean = false,
    url: String = ME_FEEDS,
): JsonElement = useHttpClient { client ->
    client.get(url) {
        header(HttpHeaders.Referrer, REFERER)

        parameter("type", type)
        parameter("relation", relation)
        parameter("show_r18", showR18)
    }
}
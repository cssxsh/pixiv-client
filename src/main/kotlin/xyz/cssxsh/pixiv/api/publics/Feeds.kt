package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.*

suspend fun PixivClient.getFeeds(
    type: String = "touch_nottext",
    relation: String = "all",
    showR18: Boolean = false,
    url: String = PublicApi.ME_FEEDS,
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): JsonElement = useHttpClient(ignore) { client ->
    client.get(url) {
        header(HttpHeaders.Referrer, PublicApi.REFERER)

        parameter("type", type)
        parameter("relation", relation)
        parameter("show_r18", showR18)
    }
}
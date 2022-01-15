package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.PixivWebClient
import xyz.cssxsh.pixiv.web.ajax

class FanBoxTag(val client: PixivWebClient) {
    companion object {
        internal const val GET_FEATURED = "https://api.fanbox.cc/tag.getFeatured?creatorId=${"official"}"
    }

    suspend fun getFeatured(creatorId: String): List<TagFeature> {
        return client.ajax(api = GET_FEATURED) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("creatorId", creatorId)
        }
    }
}
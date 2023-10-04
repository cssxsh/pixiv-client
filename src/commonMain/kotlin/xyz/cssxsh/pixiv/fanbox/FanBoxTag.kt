package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

public class FanBoxTag(override val client: PixivWebClient) : FanBoxApi() {
    public companion object {
        internal const val GET_FEATURED = "https://api.fanbox.cc/tag.getFeatured?creatorId=${"official"}"
    }

    public suspend fun getFeatured(creatorId: String): List<TagFeature> {
        return client.ajax(api = GET_FEATURED) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("creatorId", creatorId)
        }
    }

    public suspend fun getFeatured(userId: Long): List<TagFeature> {
        return client.ajax(api = GET_FEATURED) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("userId", userId)
        }
    }
}
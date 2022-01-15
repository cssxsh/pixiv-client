package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

class FanBoxPost(val client: PixivWebClient) {
    companion object {
        internal const val LIST_SUPPORTING = "https://api.fanbox.cc/post.listSupporting"

        internal const val LIST_CREATOR = "https://api.fanbox.cc/post.listCreator?creatorId=${"official"}"

        internal const val LIST_TAGGED = "https://api.fanbox.cc/post.listTagged?tag=${""}&userId=${11}"

        internal const val LIST_HOME = "https://api.fanbox.cc/post.listHome?limit=10"

        internal const val INFO = "https://api.fanbox.cc/post.info?postId=${0}"

        internal const val GET_PROMOTION = "https://api.fanbox.cc/post.getPromotion"

        internal const val PAGINATE_CREATOR = "https://api.fanbox.cc/post.paginateCreator?creatorId=official"
    }

    suspend fun getPromotion(): JsonElement {
        return client.ajax(api = GET_PROMOTION) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    suspend fun listCreator(creatorId: String): CreatorList {
        return client.ajax(api = LIST_CREATOR) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")


            parameter("creatorId", creatorId)
        }
    }

    suspend fun listHome(): CreatorList {
        return client.ajax(api = LIST_HOME) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    suspend fun listTagged(tag: String, userId: Long): CreatorList {
        return client.ajax(api = LIST_TAGGED) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("tag", tag)
            parameter("userId", userId)
        }
    }

    suspend fun listSupporting(): CreatorList {
        return client.ajax(api = LIST_SUPPORTING) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    suspend fun info(postId: Long): CreatorList {
        return client.ajax(api = INFO) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("postId", postId)
        }
    }

    suspend fun paginateCreator(): List<String> {
        return client.ajax(api = PAGINATE_CREATOR) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }
}
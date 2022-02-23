package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

public class FanBoxPost(override val client: PixivWebClient) : FanBoxApi() {
    public companion object {
        internal const val LIST_SUPPORTING = "https://api.fanbox.cc/post.listSupporting"

        internal const val LIST_CREATOR = "https://api.fanbox.cc/post.listCreator"

        internal const val LIST_TAGGED = "https://api.fanbox.cc/post.listTagged"

        internal const val LIST_HOME = "https://api.fanbox.cc/post.listHome"

        internal const val LIST_COMMENTS = "https://api.fanbox.cc/post.listComments"

        internal const val INFO = "https://api.fanbox.cc/post.info"

        internal const val GET_PROMOTION = "https://api.fanbox.cc/post.getPromotion"

        internal const val PAGINATE_CREATOR = "https://api.fanbox.cc/post.paginateCreator"
    }

    public suspend fun getPromotion(): JsonElement {// TODO: ...
        return client.ajax(api = GET_PROMOTION) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    public suspend fun listCreator(creatorId: String, maxPublishedDatetime: String?, maxId: Long?, limit: Int = 10): PostList {
        return client.ajax(api = LIST_CREATOR) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("creatorId", creatorId)
            parameter("maxPublishedDatetime", maxPublishedDatetime)
            parameter("maxId", maxId)
            parameter("limit", limit)
        }
    }

    public suspend fun listCreator(userId: Long, maxPublishedDatetime: String?, maxId: Long?, limit: Int = 10): PostList {
        return client.ajax(api = LIST_CREATOR) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("userId", userId)
            parameter("maxPublishedDatetime", maxPublishedDatetime)
            parameter("maxId", maxId)
            parameter("limit", limit)
        }
    }

    public suspend fun listComments(postId: Long, limit: Int = 10): CommentList {
        return client.ajax(api = LIST_COMMENTS) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("postId", postId)
            parameter("limit", limit)
        }
    }

    public suspend fun listHome(limit: Int = 10): PostList {
        return client.ajax(api = LIST_HOME) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("limit", limit)
        }
    }

    public suspend fun listTagged(tag: String, creatorId: String): PostList {
        return client.ajax(api = LIST_TAGGED) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("tag", tag)
            parameter("creatorId", creatorId)
        }
    }

    public suspend fun listTagged(tag: String, userId: Long): PostList {
        return client.ajax(api = LIST_TAGGED) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("tag", tag)
            parameter("userId", userId)
        }
    }

    public suspend fun listSupporting(limit: Int = 10): PostList {
        return client.ajax(api = LIST_SUPPORTING) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("limit", limit)
        }
    }

    public suspend fun info(postId: Long): PostDetail {
        return client.ajax(api = INFO) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("postId", postId)
        }
    }

    public suspend fun paginateCreator(creatorId: String): Flow<PostList> {
        val pages: List<String> = client.ajax(api = PAGINATE_CREATOR) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("creatorId", creatorId)
        }

        return pages.asFlow().map { page ->
            client.ajax(api = page) {
                header(HttpHeaders.Origin, "https://www.fanbox.cc")
                header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
            }
        }
    }

    public suspend fun paginateCreator(userId: Long): Flow<PostList> {
        val pages: List<String> = client.ajax(api = PAGINATE_CREATOR) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("userId", userId)
        }

        return pages.asFlow().map { page ->
            client.ajax(api = page) {
                header(HttpHeaders.Origin, "https://www.fanbox.cc")
                header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
            }
        }
    }
}
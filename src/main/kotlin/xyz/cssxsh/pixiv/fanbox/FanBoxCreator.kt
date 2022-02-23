package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

public class FanBoxCreator(override val client: PixivWebClient) : FanBoxApi() {
    public companion object {
        internal const val GET = "https://api.fanbox.cc/creator.get"

        internal const val GET_START_COMMENTS = "https://api.fanbox.cc/creator.getStartComments"

        internal const val LIST_RECOMMENDED = "https://api.fanbox.cc/creator.listRecommended"

        internal const val LIST_FOLLOWING = "https://api.fanbox.cc/creator.listFollowing"

        internal const val LIST_PIXIV = "https://api.fanbox.cc/creator.listPixiv"

        internal const val LIST_TWITTER = "https://api.fanbox.cc/creator.listTwitter"

        internal const val LIST_RELATED = "https://api.fanbox.cc/creator.listRelated"

        /**
         * * `https://www.fanbox.cc/@official`
         * * `https://official.fanbox.cc`
         */
        public val URL_FANBOX_CREATOR_REGEX: Regex =
            """((?<=\.fanbox\.cc/@)[\w-]{3,16})|([\w-]{3,16}(?=\.fanbox\.cc))""".toRegex()
    }

    public suspend fun get(creatorId: String): CreatorDetail {
        return client.ajax(api = GET) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("creatorId", creatorId)
        }
    }

    public suspend fun get(userId: Long): CreatorDetail {
        return client.ajax(api = GET) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("userId", userId)
        }
    }

    public suspend fun listRecommended(): List<CreatorDetail> {
        return client.ajax(api = LIST_RECOMMENDED) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    public suspend fun listFollowing(): List<CreatorDetail> {
        return client.ajax(api = LIST_FOLLOWING) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    public suspend fun listPixiv(): List<CreatorDetail> {
        return client.ajax(api = LIST_PIXIV) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    public suspend fun listTwitter(): List<CreatorDetail> {
        return client.ajax(api = LIST_TWITTER) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    public suspend fun listRelated(creatorId: String): List<CreatorDetail> {
        return client.ajax(api = LIST_RELATED) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("creatorId", creatorId)
            parameter("limit", null)
            parameter("method", "diverse")
        }
    }

    public suspend fun listRelated(userId: Long): List<CreatorDetail> {
        return client.ajax(api = LIST_RELATED) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("userId", userId)
            parameter("limit", null)
            parameter("method", "diverse")
        }
    }

    public suspend fun getStartComments(): Map<String, List<CreatorInfo>> {
        return client.ajax(api = GET_START_COMMENTS) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }
}


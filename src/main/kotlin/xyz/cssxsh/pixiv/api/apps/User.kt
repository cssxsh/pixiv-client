package xyz.cssxsh.pixiv.api.apps

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.data.apps.*

suspend fun PixivClient.userBookmarksIllust(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    maxBookmarkId: Long? = null,
    url: String = USER_BOOKMARKS_ILLUST,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("restrict", restrict.value())
        parameter("max_bookmark_id", maxBookmarkId)
    }
}

suspend fun PixivClient.userBookmarksNovel(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    maxBookmarkId: Long? = null,
    url: String = USER_BOOKMARKS_NOVEL,
): NovelData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("restrict", restrict.value())
        parameter("max_bookmark_id", maxBookmarkId)
    }
}

suspend fun PixivClient.userBlacklist(
    filter: String = "for_ios",
    url: String = USER_BLACKLIST,
): Blacklist = useHttpClient { client ->
    client.get(url) {
        parameter("filter", filter)
    }
}

suspend fun PixivClient.userBookmarksTagsIllust(
    restrict: PublicityType = PublicityType.PUBLIC,
    url: String = USER_BOOKMARKS_TAGS_ILLUST,
): BookmarkTagData = useHttpClient { client ->
    client.get(url) {
        parameter("restrict", restrict.value())
    }
}

suspend fun PixivClient.userBookmarksTagsNovel(
    restrict: PublicityType = PublicityType.PUBLIC,
    url: String = USER_BOOKMARKS_TAGS_NOVEL,
): BookmarkTagData = useHttpClient { client ->
    client.get(url) {
        parameter("restrict", restrict.value())
    }
}

suspend fun PixivClient.userDetail(
    uid: Long,
    filter: String = "for_ios",
    url: String = USER_DETAIL,
): UserDetail = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("filter", filter)
    }
}

suspend fun PixivClient.userFollowAdd(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    url: String = USER_FOLLOW_ADD,
): JsonElement = useHttpClient { client ->
    client.post(url) {
        body = FormDataContent(Parameters.build {
            append("user_id", uid.toString())
            append("restrict", restrict.value())
        })
    }
}

suspend fun PixivClient.userFollowDelete(
    uid: Long,
    url: String = USER_FOLLOW_DELETE,
): JsonElement = useHttpClient { client ->
    client.post(url) {
        body = FormDataContent(Parameters.build {
            append("user_id", uid.toString())
        })
    }
}

suspend fun PixivClient.userFollower(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = USER_FOLLOWER,
): PreviewData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.userFollowing(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = USER_FOLLOWING,
): PreviewData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.userIllusts(
    uid: Long,
    type: WorkContentType? = null,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = USER_ILLUSTS,
): IllustData =  useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("type", type?.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}


suspend fun PixivClient.userMyPixiv(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = USER_MYPIXIV,
): PreviewData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.userRecommended(
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = USER_RECOMMENDED,
): PreviewData =  useHttpClient { client ->
    client.get(url) {
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

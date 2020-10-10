package xyz.cssxsh.pixiv.api.app

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.PublicityType
import xyz.cssxsh.pixiv.WorkType
import xyz.cssxsh.pixiv.data.app.*
import xyz.cssxsh.pixiv.useHttpClient

suspend fun PixivClient.userBookmarksIllust(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    maxBookmarkId: Long? = null
): IllustData = useHttpClient { client ->
    client.get(AppApiUrls.userBookmarksIllust) {
        parameter("user_id", uid)
        parameter("restrict", restrict.value())
        parameter("max_bookmark_Id", maxBookmarkId)
    }
}

suspend fun PixivClient.userBookmarksNovel(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    maxBookmarkId: Long? = null
): NovelData = useHttpClient { client ->
    client.get(AppApiUrls.userBookmarksNovel) {
        parameter("user_id", uid)
        parameter("restrict", restrict.value())
        parameter("max_bookmark_Id", maxBookmarkId)
    }
}

suspend fun PixivClient.userBlacklist(
    filter: String = "for_ios",
): Blacklist = useHttpClient { client ->
    client.get(AppApiUrls.userBlacklist) {
        parameter("filter", filter)
    }
}

suspend fun PixivClient.userBookmarksTagsIllust(
    restrict: PublicityType = PublicityType.PUBLIC
): BookmarkTagData = useHttpClient { client ->
    client.get(AppApiUrls.userBookmarksTagsIllust) {
        parameter("restrict", restrict.value())
    }
}

suspend fun PixivClient.userBookmarksTagsNovel(
    restrict: PublicityType = PublicityType.PUBLIC
): BookmarkTagData = useHttpClient { client ->
    client.get(AppApiUrls.userBookmarksTagsNovel) {
        parameter("restrict", restrict.value())
    }
}

suspend fun PixivClient.userDetail(
    uid: Long,
    filter: String = "for_ios"
): UserDetail = useHttpClient { client ->
    client.get(AppApiUrls.userDetail) {
        parameter("user_id", uid)
        parameter("filter", filter)
    }
}

suspend fun PixivClient.userFollowAdd(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC
): JsonElement = useHttpClient { client ->
    client.post(AppApiUrls.userFollowAdd) {
        body = FormDataContent(Parameters.build {
            append("user_id", uid.toString())
            append("restrict", restrict.value())
        })
    }
}

suspend fun PixivClient.userFollowDelete(
    uid: Long
): JsonElement = useHttpClient { client ->
    client.post(AppApiUrls.userFollowDelete) {
        body = FormDataContent(Parameters.build {
            append("user_id", uid.toString())
        })
    }
}

suspend fun PixivClient.userFollower(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0
): PreviewData = useHttpClient { client ->
    client.get(AppApiUrls.userFollower) {
        parameter("user_id", uid)
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.userFollowing(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0
): PreviewData = useHttpClient { client ->
    client.get(AppApiUrls.userFollowing) {
        parameter("user_id", uid)
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.userIllusts(
    uid: Long,
    type: WorkType = WorkType.ILLUST,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData =  useHttpClient { client ->
    client.get(AppApiUrls.userIllusts) {
        parameter("user_id", uid)
        parameter("type", type.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}


suspend fun PixivClient.userMyPixiv(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0
): PreviewData = useHttpClient { client ->
    client.get(AppApiUrls.userMyPixiv) {
        parameter("user_id", uid)
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.userRecommended(
    filter: String = "for_ios",
    offset: Long = 0
): PreviewData =  useHttpClient { client ->
    client.get(AppApiUrls.userRecommended) {
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

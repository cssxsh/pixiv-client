package xyz.cssxsh.pixiv.apps

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*

public suspend fun PixivAppClient.userBlacklist(
    uid: Long,
    url: String = USER_BLACKLIST,
): Blacklist = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
    }.body()
}

public suspend fun PixivAppClient.userBookmarksIllust(
    uid: Long,
    tag: String? = null,
    restrict: PublicityType = PublicityType.PUBLIC,
    max: Long? = null,
    filter: FilterType? = null,
    url: String = USER_BOOKMARKS_ILLUST,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("tag", tag)
        parameter("restrict", restrict)
        parameter("max_bookmark_id", max)
        parameter("filter", filter)
    }.body()
}

public suspend fun PixivAppClient.userBookmarksNovel(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    max: Long? = null,
    filter: FilterType? = null,
    url: String = USER_BOOKMARKS_NOVEL,
): NovelData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("restrict", restrict)
        parameter("max_bookmark_id", max)
        parameter("filter", filter)
    }.body()
}

public suspend fun PixivAppClient.userBookmarksTagsIllust(
    restrict: PublicityType = PublicityType.PUBLIC,
    offset: Long = 0,
    url: String = USER_BOOKMARKS_TAGS_ILLUST,
): BookmarkTagData = useHttpClient { client ->
    client.get(url) {
        parameter("restrict", restrict)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.userBookmarksTagsNovel(
    restrict: PublicityType = PublicityType.PUBLIC,
    offset: Long = 0,
    url: String = USER_BOOKMARKS_TAGS_NOVEL,
): BookmarkTagData = useHttpClient { client ->
    client.get(url) {
        parameter("restrict", restrict)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.userDetail(
    uid: Long,
    filter: FilterType? = null,
    url: String = USER_DETAIL,
): UserDetail = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("filter", filter)
    }.body()
}

public suspend fun PixivAppClient.userFollowAdd(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    url: String = USER_FOLLOW_ADD,
): JsonElement = useHttpClient { client ->
    client.submitForm(url, Parameters.build {
        append("user_id", uid.toString())
        append("restrict", restrict.toString())
    }).body()
}

public suspend fun PixivAppClient.userFollowDelete(
    uid: Long,
    url: String = USER_FOLLOW_DELETE,
): JsonElement = useHttpClient { client ->
    client.submitForm(url, Parameters.build {
        append("user_id", uid.toString())
    }).body()
}

public suspend fun PixivAppClient.userFollower(
    uid: Long,
    filter: FilterType? = null,
    offset: Long = 0,
    url: String = USER_FOLLOWER,
): PreviewData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("filter", filter)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.userFollowing(
    uid: Long,
    filter: FilterType? = null,
    offset: Long = 0,
    url: String = USER_FOLLOWING,
): PreviewData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("filter", filter)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.userIllusts(
    uid: Long,
    type: WorkContentType? = null,
    filter: FilterType? = null,
    offset: Long = 0,
    url: String = USER_ILLUSTS,
): UserIllustData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("type", type)
        parameter("filter", filter)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.userMyPixiv(
    uid: Long,
    filter: FilterType? = null,
    offset: Long = 0,
    url: String = USER_MYPIXIV,
): PreviewData = useHttpClient { client ->
    client.get(url) {
        parameter("user_id", uid)
        parameter("filter", filter)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.userRecommended(
    filter: FilterType? = null,
    offset: Long = 0,
    url: String = USER_RECOMMENDED,
): PreviewData = useHttpClient { client ->
    client.get(url) {
        parameter("filter", filter)
        parameter("offset", offset)
    }.body()
}

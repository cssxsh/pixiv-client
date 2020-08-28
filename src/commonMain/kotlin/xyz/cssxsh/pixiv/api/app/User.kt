package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonElementSerializer
import xyz.cssxsh.pixiv.Method
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.PublicityType
import xyz.cssxsh.pixiv.WorkType
import xyz.cssxsh.pixiv.data.app.*

suspend fun PixivClient.userBookmarksIllust(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    maxBookmarkId: Long? = null
): IllustData = useRESTful(
    method = Method.GET,
    deserializer = IllustData.serializer(),
    apiUrl = AppApiUrls.userBookmarksIllust,
    paramsMap = mapOf(
        "user_id" to uid,
        "restrict" to restrict.value(),
        "max_bookmark_Id" to maxBookmarkId
    )
)

suspend fun PixivClient.userBookmarksNovel(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    maxBookmarkId: Long? = null
): NovelData = useRESTful(
    method = Method.GET,
    deserializer = NovelData.serializer(),
    apiUrl = AppApiUrls.userBookmarksNovel,
    paramsMap = mapOf(
        "user_id" to uid,
        "restrict" to restrict.value(),
        "max_bookmark_Id" to maxBookmarkId
    )
)

suspend fun PixivClient.userBlacklist(
    filter: String = "for_ios",
): Blacklist = useRESTful(
    method = Method.GET,
    deserializer = Blacklist.serializer(),
    apiUrl = AppApiUrls.userBlacklist,
    paramsMap = mapOf(
        "filter" to filter
    )
)

suspend fun PixivClient.userBookmarksTagsIllust(
    restrict: PublicityType = PublicityType.PUBLIC
): BookmarkTagData = useRESTful(
    method = Method.GET,
    deserializer = BookmarkTagData.serializer(),
    apiUrl = AppApiUrls.userBookmarksTagsIllust,
    paramsMap = mapOf(
        "restrict" to restrict
    )
)

suspend fun PixivClient.userBookmarksTagsNovel(
    restrict: PublicityType = PublicityType.PUBLIC
): BookmarkTagData = useRESTful(
    method = Method.GET,
    deserializer = BookmarkTagData.serializer(),
    apiUrl = AppApiUrls.userBookmarksTagsNovel,
    paramsMap = mapOf(
        "restrict" to restrict
    )
)

suspend fun PixivClient.userDetail(
    uid: Long,
    filter: String = "for_ios"
): UserDetail = useRESTful(
    method = Method.GET,
    deserializer = UserDetail.serializer(),
    apiUrl = AppApiUrls.userDetail,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter
    )
)

suspend fun PixivClient.userFollowAdd(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC
): JsonElement = useRESTful(
    method = Method.POST,
    deserializer = JsonElementSerializer,
    apiUrl = AppApiUrls.userFollowAdd,
    paramsMap = mapOf(
        "user_id" to uid,
        "restrict" to restrict
    )
)

suspend fun PixivClient.userFollowDelete(
    uid: Long
): JsonElement = useRESTful(
    method = Method.POST,
    deserializer = JsonElementSerializer,
    apiUrl = AppApiUrls.userFollowDelete,
    paramsMap = mapOf(
        "user_id" to uid
    )
)

suspend fun PixivClient.userFollower(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0
): PreviewData = useRESTful(
    method = Method.GET,
    deserializer = PreviewData.serializer(),
    apiUrl = AppApiUrls.userFollower,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.userFollowing(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0
): PreviewData = useRESTful(
    method = Method.GET,
    deserializer = PreviewData.serializer(),
    apiUrl = AppApiUrls.userFollowing,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.userIllusts(
    uid: Long,
    type: WorkType = WorkType.ILLUST,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = useRESTful(
    method = Method.GET,
    deserializer = IllustData.serializer(),
    apiUrl = AppApiUrls.userIllusts,
    paramsMap = mapOf(
        "user_id" to uid,
        "type" to type.value(),
        "filter" to filter,
        "offset" to offset
    )
)


suspend fun PixivClient.userMyPixiv(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0
): PreviewData = useRESTful(
    method = Method.GET,
    deserializer = PreviewData.serializer(),
    apiUrl = AppApiUrls.userMyPixiv,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.userRecommended(
    filter: String = "for_ios",
    offset: Long = 0
): PreviewData = useRESTful(
    method = Method.GET,
    deserializer = PreviewData.serializer(),
    apiUrl = AppApiUrls.userRecommended,
    paramsMap = mapOf(
        "filter" to filter,
        "offset" to offset
    )
)
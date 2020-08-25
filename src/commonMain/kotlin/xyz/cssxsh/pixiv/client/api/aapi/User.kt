package xyz.cssxsh.pixiv.client.api.aapi

import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.client.PublicityType
import xyz.cssxsh.pixiv.client.WorkType
import xyz.cssxsh.pixiv.client.data.aapi.IllustsData
import xyz.cssxsh.pixiv.client.data.aapi.NovelsData
import xyz.cssxsh.pixiv.client.data.aapi.RecommendedInfo

suspend fun PixivClient.getUserIllusts(
    uid: Long,
    type: WorkType = WorkType.ILLUST,
    filter: String = "for_ios",
    offset: Long = 0
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.userIllusts,
    paramsMap = mapOf(
        "user_id" to uid,
        "type" to type.value(),
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.getUserBookmarksIllust(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    maxBookmarkId: Long? = null
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.userBookmarksIllust,
    paramsMap = mapOf(
        "user_id" to uid,
        "restrict" to restrict.value(),
        "max_bookmark_Id" to maxBookmarkId
    )
)

suspend fun PixivClient.getUserBookmarksNovel(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC,
    maxBookmarkId: Long? = null
): NovelsData = httpGet(
    deserialize = NovelsData.serializer(),
    apiUrl = AppApiUrls.userBookmarksNovel,
    paramsMap = mapOf(
        "user_id" to uid,
        "restrict" to restrict.value(),
        "max_bookmark_Id" to maxBookmarkId
    )
)

suspend fun PixivClient.getUserRecommended(
    filter: String = "for_ios",
    offset: Long = 0
): RecommendedInfo = httpGet(
    deserialize = RecommendedInfo.serializer(),
    apiUrl = AppApiUrls.userRecommended,
    paramsMap = mapOf(
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.getUserFollowing(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0
): RecommendedInfo = httpGet(
    deserialize = RecommendedInfo.serializer(),
    apiUrl = AppApiUrls.userFollowing,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.getUserFollower(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0
): RecommendedInfo = httpGet(
    deserialize = RecommendedInfo.serializer(),
    apiUrl = AppApiUrls.userFollower,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.getUserMypixiv(
    uid: Long,
    filter: String = "for_ios",
    offset: Long = 0
): RecommendedInfo = httpGet(
    deserialize = RecommendedInfo.serializer(),
    apiUrl = AppApiUrls.userMypixiv,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter,
        "offset" to offset
    )
)
package xyz.cssxsh.pixiv.client.api.aapi

import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.client.PublicityType
import xyz.cssxsh.pixiv.client.data.aapi.IllustsData
import xyz.cssxsh.pixiv.client.data.aapi.NovelsData
import xyz.cssxsh.pixiv.client.data.aapi.RecommendedInfo


suspend fun PixivClient.getUserIllusts(
    uid: Long,
    type: String = "illust", // FIXME: Enmu [illust, novel]
    filter: String = "for_ios"
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.userIllusts,
    paramsMap = mapOf(
        "user_id" to uid,
        "type" to type,
        "filter" to filter
    )
)

suspend fun PixivClient.getUserBookmarksIllust(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.userBookmarksIllust,
    paramsMap = mapOf(
        "user_id" to uid,
        "restrict" to restrict
    )
)

suspend fun PixivClient.getUserBookmarksNovel(
    uid: Long,
    restrict: PublicityType = PublicityType.PUBLIC
): NovelsData = httpGet(
    deserialize = NovelsData.serializer(),
    apiUrl = AppApiUrls.userBookmarksNovel,
    paramsMap = mapOf(
        "user_id" to uid,
        "restrict" to restrict
    )
)

suspend fun PixivClient.getUserRecommended(
    filter: String = "for_ios"
): RecommendedInfo = httpGet(
    deserialize = RecommendedInfo.serializer(),
    apiUrl = AppApiUrls.userRecommended,
    paramsMap = mapOf(
        "filter" to filter
    )
)

suspend fun PixivClient.getUserFollowing(
    uid: Long,
    filter: String = "for_ios"
): RecommendedInfo = httpGet(
    deserialize = RecommendedInfo.serializer(),
    apiUrl = AppApiUrls.userFollowing,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter
    )
)

suspend fun PixivClient.getUserFollower(
    uid: Long,
    filter: String = "for_ios"
): RecommendedInfo = httpGet(
    deserialize = RecommendedInfo.serializer(),
    apiUrl = AppApiUrls.userFollower,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter
    )
)

suspend fun PixivClient.getUserMypixiv(
    uid: Long,
    filter: String = "for_ios"
): RecommendedInfo = httpGet(
    deserialize = RecommendedInfo.serializer(),
    apiUrl = AppApiUrls.userMypixiv,
    paramsMap = mapOf(
        "user_id" to uid,
        "filter" to filter
    )
)
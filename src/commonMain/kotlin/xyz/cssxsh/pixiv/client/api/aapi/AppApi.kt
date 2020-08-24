package xyz.cssxsh.pixiv.client.api.aapi

import xyz.cssxsh.pixiv.client.*
import xyz.cssxsh.pixiv.client.data.aapi.*


suspend fun PixivClient.getIllustFollow(
    restrict: PublicityType = PublicityType.PUBLIC
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.illustFollow,
    paramsMap = mapOf(
        "restrict" to restrict
    )
)

suspend fun PixivClient.getIllustNew(
    contentType: String = "illust", // XXX: ENUM
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios"
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.illustNew,
    paramsMap = mapOf(
        "content_type" to contentType,
        "restrict" to restrict,
        "filter" to filter
    )
)


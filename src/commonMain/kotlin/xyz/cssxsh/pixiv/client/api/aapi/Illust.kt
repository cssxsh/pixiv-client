package xyz.cssxsh.pixiv.client.api.aapi

import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.client.PublicityType
import xyz.cssxsh.pixiv.client.ContentType
import xyz.cssxsh.pixiv.client.data.aapi.IllustsData

suspend fun PixivClient.getIllustFollow(
    restrict: PublicityType = PublicityType.PUBLIC,
    offset: Long = 0
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.illustFollow,
    paramsMap = mapOf(
        "restrict" to restrict.value(),
        "offset" to offset
    )
)

suspend fun PixivClient.getIllustNew(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.illustNew,
    paramsMap = mapOf(
        "content_type" to contentType.value(),
        "restrict" to restrict.value(),
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.getIllustMypixiv(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.illustMypixiv,
    paramsMap = mapOf(
        "content_type" to contentType.value(),
        "restrict" to restrict.value(),
        "filter" to filter,
        "offset" to offset
    )
)


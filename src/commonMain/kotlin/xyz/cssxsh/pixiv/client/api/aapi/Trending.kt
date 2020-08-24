package xyz.cssxsh.pixiv.client.api.aapi

import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.client.data.aapi.TrendTagsData

suspend fun PixivClient.getTrendingTagsIllust(
    filter: String = "for_ios"
): TrendTagsData = httpGet(
    deserialize = TrendTagsData.serializer(),
    apiUrl = AppApiUrls.trendingTagsIllust,
    paramsMap = mapOf(
        "filter" to filter
    )
)

suspend fun PixivClient.getTrendingTagsNovel(
    filter: String = "for_ios"
): TrendTagsData = httpGet(
    deserialize = TrendTagsData.serializer(),
    apiUrl = AppApiUrls.trendingTagsNovel,
    paramsMap = mapOf(
        "filter" to filter
    )
)
package xyz.cssxsh.pixiv.client.api.aapi

import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.client.WorkType
import xyz.cssxsh.pixiv.client.data.aapi.TrendTagsData

suspend fun PixivClient.getTrendingTags(
    type: WorkType,
    filter: String = "for_ios"
): TrendTagsData = httpGet(
    deserialize = TrendTagsData.serializer(),
    apiUrl = AppApiUrls.trendingTags(type),
    paramsMap = mapOf(
        "filter" to filter
    )
)
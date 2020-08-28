package xyz.cssxsh.pixiv.api.app

import xyz.cssxsh.pixiv.Method
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.TrendTagsData

suspend fun PixivClient.trendingTagsIllust(
    filter: String = "for_ios"
): TrendTagsData = useRESTful(
    method = Method.GET,
    deserializer = TrendTagsData.serializer(),
    apiUrl = AppApiUrls.trendingTagsIllust,
    paramsMap = mapOf(
        "filter" to filter
    )
)

suspend fun PixivClient.trendingTagsNovel(
    filter: String = "for_ios"
): TrendTagsData = useRESTful(
    method = Method.GET,
    deserializer = TrendTagsData.serializer(),
    apiUrl = AppApiUrls.trendingTagsNovel,
    paramsMap = mapOf(
        "filter" to filter
    )
)
package xyz.cssxsh.pixiv.api.app

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.TrendTagData
import xyz.cssxsh.pixiv.useHttpClient

suspend fun PixivClient.trendingTagsIllust(
    filter: String = "for_ios"
): TrendTagData = useHttpClient { client ->
    client.get(AppApiUrls.trendingTagsIllust) {
        parameter("filter", filter)
    }
}

suspend fun PixivClient.trendingTagsNovel(
    filter: String = "for_ios"
): TrendTagData = useHttpClient { client ->
    client.get(AppApiUrls.trendingTagsNovel) {
        parameter("filter", filter)
    }
}
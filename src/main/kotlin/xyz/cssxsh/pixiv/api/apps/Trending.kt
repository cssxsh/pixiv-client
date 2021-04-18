package xyz.cssxsh.pixiv.api.apps

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.apps.TrendTagData

suspend fun PixivClient.trendingTagsIllust(
    filter: String = "for_ios",
    url: String = TRENDING_TAGS_ILLUST,
): TrendTagData = useHttpClient  { client ->
    client.get(url) {
        parameter("filter", filter)
    }
}

suspend fun PixivClient.trendingTagsNovel(
    filter: String = "for_ios",
    url: String = TRENDING_TAGS_NOVEL,
): TrendTagData = useHttpClient  { client ->
    client.get(url) {
        parameter("filter", filter)
    }
}
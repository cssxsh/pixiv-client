package xyz.cssxsh.pixiv.apps

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*

suspend fun PixivAppClient.trendingTagsIllust(
    filter: FilterType? = null,
    url: String = TRENDING_TAGS_ILLUST,
): TrendIllustData = useHttpClient  { client ->
    client.get(url) {
        parameter("filter", filter?.value())
    }
}
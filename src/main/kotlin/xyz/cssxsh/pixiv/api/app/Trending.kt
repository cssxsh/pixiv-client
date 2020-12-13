package xyz.cssxsh.pixiv.api.app

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.TrendTagData
import xyz.cssxsh.pixiv.useHttpClient

suspend fun PixivClient.trendingTagsIllust(
    filter: String = "for_ios",
    url: String = AppApi.TRENDING_TAGS_ILLUST,
    ignore: (Throwable) -> Boolean = { _ -> false },
): TrendTagData = useHttpClient(ignore)  { client ->
    client.get(url) {
        parameter("filter", filter)
    }
}

suspend fun PixivClient.trendingTagsNovel(
    filter: String = "for_ios",
    url: String = AppApi.TRENDING_TAGS_NOVEL,
    ignore: (Throwable) -> Boolean = { _ -> false },
): TrendTagData = useHttpClient(ignore)  { client ->
    client.get(url) {
        parameter("filter", filter)
    }
}
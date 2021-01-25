package xyz.cssxsh.pixiv.api.apps

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.apps.TrendTagData
import xyz.cssxsh.pixiv.useHttpClient

suspend fun PixivClient.trendingTagsIllust(
    filter: String = "for_ios",
    url: String = AppApi.TRENDING_TAGS_ILLUST,
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): TrendTagData = useHttpClient(ignore)  { client ->
    client.get(url) {
        parameter("filter", filter)
    }
}

suspend fun PixivClient.trendingTagsNovel(
    filter: String = "for_ios",
    url: String = AppApi.TRENDING_TAGS_NOVEL,
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): TrendTagData = useHttpClient(ignore)  { client ->
    client.get(url) {
        parameter("filter", filter)
    }
}
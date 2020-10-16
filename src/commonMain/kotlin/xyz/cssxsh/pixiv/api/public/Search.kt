package xyz.cssxsh.pixiv.api.public

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.public.ListArtData

suspend fun PixivClient.getSearchWorks(
    word: String,
    mode: SearchMode = SearchMode.EXACT_TAG,
    sort: SortMode = SortMode.DATE,
    order: OrderType = OrderType.DESC,
    period: PeriodType = PeriodType.ALL,
    page: Int = 1,
    perPageNum: Int = 30,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf(),
    url: String = PublicApi.SEARCH_WORKS
): ListArtData = useHttpClient { client ->
    client.get(url) {
        header(HttpHeaders.Referrer, PublicApi.REFERER)

        parameter("q", word)
        parameter("mode", mode.value())
        parameter("sort", sort.value())
        parameter("order", order.value())
        parameter("period", period.value())
        parameter("page", page)
        parameter("per_page", perPageNum)
        parameter("include_stats", includeStats)
        parameter("include_sanity_level", includeSanityLevel)
        parameter("imageSizes", imageSizes.joinToString(","))
        parameter("profileImageSizes", profileImageSizes.joinToString(","))
    }
}
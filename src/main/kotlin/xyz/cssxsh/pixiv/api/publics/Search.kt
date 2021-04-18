package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.publics.ListArtData

suspend fun PixivClient.getSearchWorks(
    word: String,
    mode: SearchMode = SearchMode.EXACT_TAG,
    sort: SortMode = SortMode.DATE,
    order: OrderType = OrderType.DESC,
    period: PeriodType = PeriodType.ALL,
    config: PublicApiConfig,
    url: String = SEARCH_WORKS,
): ListArtData = useHttpClient { client ->
    client.get(url) {
        init(config)

        parameter("q", word)
        parameter("mode", mode.value())
        parameter("sort", sort.value())
        parameter("order", order.value())
        parameter("period", period.value())
    }
}
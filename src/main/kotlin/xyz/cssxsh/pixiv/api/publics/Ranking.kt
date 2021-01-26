package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.publics.RankingData

suspend fun PixivClient.getRankingWorks(
    type: PublicApi.RankingType,
    mode: String,
    date: String? = null,
    config: PublicApiConfig,
    url: String = PublicApi.RANKING(type),
): RankingData = useHttpClient { client ->
    client.get(url) {
        init(config)

        parameter("mode", mode)
        parameter("date", date)
    }
}
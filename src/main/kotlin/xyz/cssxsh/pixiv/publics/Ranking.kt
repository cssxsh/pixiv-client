package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*

suspend fun PixivAppClient.getRankingWorks(
    type: RankingType,
    mode: String,
    date: String? = null,
    config: PublicApiConfig,
    url: String = RANKING(type),
): RankingData = useHttpClient { client ->
    client.get(url) {
        init(config)

        parameter("mode", mode)
        parameter("date", date)
    }
}
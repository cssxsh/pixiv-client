package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.publics.RankingData
import xyz.cssxsh.pixiv.*

suspend fun PixivClient.getRankingWorks(
    type: PublicApi.RankingType,
    mode: String,
    date: String? = null,
    config: PublicApiConfig,
    url: String = PublicApi.RANKING(type),
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): RankingData = useHttpClient(ignore) { client ->
    client.get(url) {
        init(config)

        parameter("mode", mode)
        parameter("date", date)
    }
}
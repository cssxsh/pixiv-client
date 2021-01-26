package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.publics.ListArtData

suspend fun PixivClient.getWorks(
    config: PublicApiConfig = PublicApiConfig(),
    url: String = PublicApi.WORKS,
): ListArtData = useHttpClient { client ->
    client.get(url) {
        init(config)
    }
}

suspend fun PixivClient.getWork(
    pid: Long,
    config: PublicApiConfig = PublicApiConfig(),
): ListArtData = getWorks(
    config = config,
    url = PublicApi.WORKS_BY_PID(pid),
)
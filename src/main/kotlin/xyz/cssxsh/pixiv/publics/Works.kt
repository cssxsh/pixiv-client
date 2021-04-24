package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.PixivClient

suspend fun PixivClient.getWorks(
    config: PublicApiConfig = PublicApiConfig(),
    url: String = WORKS,
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
    url = WORKS_BY_PID(pid),
)
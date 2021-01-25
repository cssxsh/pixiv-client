package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.publics.ListArtData
import xyz.cssxsh.pixiv.*

suspend fun PixivClient.getWorks(
    config: PublicApiConfig = PublicApiConfig(),
    url: String = PublicApi.WORKS,
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): ListArtData = useHttpClient(ignore) { client ->
    client.get(url) {
        init(config)
    }
}

suspend fun PixivClient.getWork(
    pid: Long,
    config: PublicApiConfig = PublicApiConfig(),
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): ListArtData = getWorks(
    config = config,
    url = PublicApi.WORKS_BY_PID(pid),
    ignore = ignore
)
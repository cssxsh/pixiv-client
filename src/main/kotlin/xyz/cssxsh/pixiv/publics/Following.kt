package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.PixivClient

suspend fun PixivClient.getFollowingWorks(
    config: PublicApiConfig,
    url: String = ME_FOLLOWING_WORKS,
): ListArtData = useHttpClient { client ->
    client.get(url) {
        init(config)
    }
}
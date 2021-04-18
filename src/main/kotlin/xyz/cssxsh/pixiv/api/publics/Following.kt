package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.publics.ListArtData

suspend fun PixivClient.getFollowingWorks(
    config: PublicApiConfig,
    url: String = ME_FOLLOWING_WORKS,
): ListArtData = useHttpClient { client ->
    client.get(url) {
        init(config)
    }
}
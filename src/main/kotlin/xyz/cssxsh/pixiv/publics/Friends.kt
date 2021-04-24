package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.PixivClient

suspend fun PixivClient.getFriendsWorks(
    config: PublicApiConfig,
    url: String = ME_FRIENDS_WORKS,
): ListArtData = useHttpClient { client ->
    client.get(url) {
        init(config)
    }
}
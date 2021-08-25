package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*

suspend fun PixivAppClient.getFriendsWorks(
    config: PublicApiConfig,
    url: String = ME_FRIENDS_WORKS,
): ListArtData = useHttpClient { client ->
    client.get(url) {
        init(config)
    }
}
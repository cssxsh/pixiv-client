package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.publics.ListArtData

suspend fun PixivClient.getFriendsWorks(
    config: PublicApiConfig,
    url: String = PublicApi.ME_FRIENDS_WORKS,
): ListArtData = useHttpClient { client ->
    client.get(url) {
        init(config)
    }
}
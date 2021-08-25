package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.*

suspend fun PixivAppClient.getUsersFavoriteWorks(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    includeWork: Boolean = true,
    config: PublicApiConfig,
): ListArtData = useHttpClient { client ->
    client.get(USERS_FAVORITE_WORKS(uid)) {
        init(config)

        parameter("publicity" , publicity.value())
        parameter("include_work" , includeWork)
    }
}


suspend fun PixivAppClient.getUserWorks(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    config: PublicApiConfig,
    url: String = USERS_WORKS(uid),
): ListArtData = useHttpClient { client ->
    client.get(url) {
        init(config)

        parameter("publicity" , publicity.value())
    }
}
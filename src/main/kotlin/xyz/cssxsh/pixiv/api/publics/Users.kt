package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.publics.ListArtData

suspend fun PixivClient.getUsersFavoriteWorks(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    includeWork: Boolean = true,
    config: PublicApiConfig,
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): ListArtData = useHttpClient(ignore) { client ->
    client.get(PublicApi.USERS_FAVORITE_WORKS(uid)) {
        init(config)

        parameter("publicity" , publicity.value())
        parameter("include_work" , includeWork)
    }
}


suspend fun PixivClient.getUserWorks(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    config: PublicApiConfig,
    url: String = PublicApi.USERS_WORKS(uid),
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): ListArtData = useHttpClient(ignore) { client ->
    client.get(url) {
        init(config)

        parameter("publicity" , publicity.value())
    }
}
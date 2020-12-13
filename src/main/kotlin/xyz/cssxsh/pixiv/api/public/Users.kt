package xyz.cssxsh.pixiv.api.public

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.public.ListArtData

suspend fun PixivClient.getUsersFavoriteWorks(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    page: Int = 1,
    perPageNum: Int = 30,
    includeWork: Boolean = true,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf(),
    ignore: (Throwable) -> Boolean = { _ -> false },
): ListArtData = useHttpClient(ignore) { client ->
    client.get(PublicApi.USERS_FAVORITE_WORKS(uid)) {
        header(HttpHeaders.Referrer, PublicApi.REFERER)

        parameter("publicity" , publicity.value())
        parameter("include_work" , includeWork)
        parameter("page", page)
        parameter("per_page", perPageNum)
        parameter("include_stats", includeStats)
        parameter("include_sanity_level", includeSanityLevel)
        parameter("imageSizes", imageSizes.joinToString(","))
        parameter("profileImageSizes", profileImageSizes.joinToString(","))
    }
}


suspend fun PixivClient.getUserWork(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    page: Int = 1,
    perPageNum: Int = 30,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf(),
    url: String = PublicApi.USERS_WORKS(uid),
    ignore: (Throwable) -> Boolean = { _ -> false },
): ListArtData = useHttpClient(ignore) { client ->
    client.get(url) {
        header(HttpHeaders.Referrer, PublicApi.REFERER)

        parameter("publicity" , publicity.value())
        parameter("page", page)
        parameter("per_page", perPageNum)
        parameter("include_stats", includeStats)
        parameter("include_sanity_level", includeSanityLevel)
        parameter("imageSizes", imageSizes.joinToString(","))
        parameter("profileImageSizes", profileImageSizes.joinToString(","))
    }
}
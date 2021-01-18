package xyz.cssxsh.pixiv.api.public

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.public.ListArtData
import xyz.cssxsh.pixiv.*

suspend fun PixivClient.getWorks(
    page: Int = 1,
    perPageNum: Int = 30,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf(),
    url: String = PublicApi.WORKS,
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): ListArtData = useHttpClient(ignore) { client ->
    client.get(url) {
        header(HttpHeaders.Referrer, PublicApi.REFERER)

        parameter("page", page)
        parameter("per_page", perPageNum)
        parameter("include_stats", includeStats)
        parameter("include_sanity_level", includeSanityLevel)
        parameter("imageSizes", imageSizes.joinToString(","))
        parameter("profileImageSizes", profileImageSizes.joinToString(","))
    }
}

suspend fun PixivClient.worksByPid(
    pid: Long,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf(),
    url: String = PublicApi.WORKS_BY_PID(pid),
    ignore: suspend (Throwable) -> Boolean = { _ -> false },
): ListArtData = useHttpClient(ignore) { client ->
    client.get(url) {
        header(HttpHeaders.Referrer, PublicApi.REFERER)

        parameter("include_stats", includeStats)
        parameter("include_sanity_level", includeSanityLevel)
        parameter("imageSizes", imageSizes.joinToString(","))
        parameter("profileImageSizes", profileImageSizes.joinToString(","))
    }
}
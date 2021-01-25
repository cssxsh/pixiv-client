package xyz.cssxsh.pixiv.api.publics

import io.ktor.client.request.*
import io.ktor.http.*

internal fun HttpRequestBuilder.init(config: PublicApiConfig) {

    header(HttpHeaders.Referrer, PublicApi.REFERER)

    parameter("page", config.page)
    parameter("per_page", config.perPageNum)
    parameter("include_stats", config.includeStats)
    parameter("include_sanity_level", config.includeSanityLevel)
    parameter("imageSizes", config.imageSizes.joinToString(","))
    parameter("profileImageSizes", config.profileImageSizes.joinToString(","))
}
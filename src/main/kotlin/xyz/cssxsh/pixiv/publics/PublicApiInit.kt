package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublicApiConfig(
    @SerialName("page")
    val page: Int = 1,
    @SerialName("per_page")
    val perPageNum: Int = 30,
    @SerialName("include_stats")
    val includeStats: Boolean = true,
    @SerialName("include_sanity_level")
    val includeSanityLevel: Boolean = true,
    @SerialName("imageSizes")
    val imageSizes: List<String> = listOf(),
    @SerialName("profileImageSizes")
    val profileImageSizes: List<String> = listOf(),
)

internal fun HttpRequestBuilder.init(config: PublicApiConfig) {

    header(HttpHeaders.Referrer, REFERER)

    parameter("page", config.page)
    parameter("per_page", config.perPageNum)
    parameter("include_stats", config.includeStats)
    parameter("include_sanity_level", config.includeSanityLevel)
    parameter("imageSizes", config.imageSizes.joinToString(","))
    parameter("profileImageSizes", config.profileImageSizes.joinToString(","))
}
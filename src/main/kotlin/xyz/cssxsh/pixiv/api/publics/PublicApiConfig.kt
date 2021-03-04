package xyz.cssxsh.pixiv.api.publics

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

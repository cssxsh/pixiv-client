package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IllustData(
    @SerialName("illusts")
    val illusts: List<IllustInfo>,
    @SerialName("next_url")
    val nextUrl: String?,
    @SerialName("search_span_limit")
    val limit: Long? = null,
)
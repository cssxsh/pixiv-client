package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IllustsData(
    @SerialName("illusts")
    val illusts: List<Illust>,
    @SerialName("next_url")
    val nextUrl: String?,
    @SerialName("search_span_limit")
    val searchSpanLimit: Long? = null
)
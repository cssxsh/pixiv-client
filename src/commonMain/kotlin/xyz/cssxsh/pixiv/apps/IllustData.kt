package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class IllustData(
    @SerialName("illusts")
    val illusts: List<IllustInfo>,
    @SerialName("next_url")
    val nextUrl: String? = null,
    @SerialName("search_span_limit")
    val limit: Long? = null,
)
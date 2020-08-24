package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NovelsData(
    @SerialName("novels")
    val novels: List<Novel>,
    @SerialName("next_url")
    val nextUrl: String?,
    @SerialName("search_span_limit")
    val searchSpanLimit: Long? = null
)
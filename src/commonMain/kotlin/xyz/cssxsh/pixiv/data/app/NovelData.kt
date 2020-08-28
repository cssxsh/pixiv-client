package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NovelData(
    @SerialName("novels")
    val novels: List<NovelInfo>,
    @SerialName("next_url")
    val nextUrl: String?,
    @SerialName("search_span_limit")
    val searchSpanLimit: Long? = null
)
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class NovelData(
    @SerialName("novels")
    val novels: List<NovelInfo>,
    @SerialName("next_url")
    val nextUrl: String?,
    @SerialName("search_span_limit")
    val limit: Long? = null,
)
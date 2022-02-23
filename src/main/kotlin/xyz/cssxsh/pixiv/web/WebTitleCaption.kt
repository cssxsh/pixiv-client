package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
public data class WebTitleCaption(
    @SerialName("workCaption")
    val caption: String?,
    @SerialName("workTitle")
    val title: String?
)
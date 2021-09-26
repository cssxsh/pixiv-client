package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
data class WebTitleCaption(
    @SerialName("workCaption")
    val caption: String?,
    @SerialName("workTitle")
    val title: String?
)
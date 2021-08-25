package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
data class BookmarkData(
    @SerialName("id")
    val id: String,
    @SerialName("private")
    val `private`: Boolean
)
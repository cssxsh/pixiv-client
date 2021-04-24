package xyz.cssxsh.pixiv.web

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkData(
    @SerialName("id")
    val id: String,
    @SerialName("private")
    val `private`: Boolean
)
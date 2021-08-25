package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
data class CommentData(
    @SerialName("comments")
    val comments: List<CommentInfo>,
    @SerialName("next_url")
    val nextUrl: String?,
)
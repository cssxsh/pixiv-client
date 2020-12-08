package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentData(
    @SerialName("comments")
    val comments: List<CommentInfo>,
    @SerialName("next_url")
    val nextUrl: String?
)
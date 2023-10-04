package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class CommentData(
    @SerialName("comments")
    val comments: List<CommentInfo>,
    @SerialName("next_url")
    val nextUrl: String?,
    @SerialName("comment_access_control")
    val commentAccessControl: Int = 0,
)
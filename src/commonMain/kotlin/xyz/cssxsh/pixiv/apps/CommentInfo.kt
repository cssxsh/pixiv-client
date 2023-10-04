package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
public data class CommentInfo(
    @SerialName("comment")
    val comment: String,
    @SerialName("date")
    val date: String,
    @SerialName("has_replies")
    val hasReplies: Boolean,
    @SerialName("id")
    val id: Long,
    @SerialName("user")
    val user: UserInfo,
    @SerialName("stamp")
    val stamp: CommentStamp? = null,
)
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import java.time.*

@Serializable
public data class CommentInfo(
    @SerialName("comment")
    val comment: String,
    @SerialName("date")
    @Contextual
    val date: OffsetDateTime,
    @SerialName("has_replies")
    val hasReplies: Boolean,
    @SerialName("id")
    val id: Long,
    @SerialName("user")
    val user: UserInfo,
    @SerialName("stamp")
    val stamp: CommentStamp? = null,
)
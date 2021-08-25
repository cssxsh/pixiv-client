package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*
import java.time.*

@Serializable
data class CommentInfo(
    @SerialName("comment")
    val comment: String,
    @SerialName("date")
    @Serializable(with = JapanDateTimeSerializer::class)
    val date: OffsetDateTime,
    @SerialName("has_replies")
    val hasReplies: Boolean,
    @SerialName("id")
    val id: Long,
    @SerialName("user")
    val user: UserInfo,
)
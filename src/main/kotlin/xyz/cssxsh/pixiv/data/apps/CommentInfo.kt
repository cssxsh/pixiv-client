package xyz.cssxsh.pixiv.data.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.data.JapanDateTimeSerializer
import java.time.OffsetDateTime

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
    val user: UserInfo
)
package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*
import java.time.*

@Serializable
public data class CommentInfo(
    @SerialName("body")
    val body: String,
    @Contextual
    @SerialName("createdDatetime")
    val createdDatetime: OffsetDateTime,
    @SerialName("id")
    val id: Long,
    @SerialName("isLiked")
    val isLiked: Boolean,
    @SerialName("isOwn")
    val isOwn: Boolean,
    @SerialName("likeCount")
    val likeCount: Int,
    @SerialName("parentCommentId")
    val parentCommentId: Long,
    @SerialName("replies")
    val replies: List<CommentInfo> = emptyList(),
    @SerialName("rootCommentId")
    val rootCommentId: Long,
    @SerialName("user")
    val user: CreatorInfo
)
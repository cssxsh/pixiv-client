package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
public data class CommentInfo(
    @SerialName("body")
    val body: String,

    @SerialName("createdDatetime")
    val createdDatetime: String,
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
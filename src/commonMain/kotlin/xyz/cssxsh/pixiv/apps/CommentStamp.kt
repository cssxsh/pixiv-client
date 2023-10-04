package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class CommentStamp(
    @SerialName("stamp_id")
    val id: Long,
    @SerialName("stamp_url")
    val url: String,
)
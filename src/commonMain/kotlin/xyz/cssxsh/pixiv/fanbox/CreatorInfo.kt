package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
public data class CreatorInfo(
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("iconUrl")
    val iconUrl: String?,
    @SerialName("name")
    val name: String,
    @SerialName("role")
    val role: String? = null,
    @SerialName("userId")
    val userId: Long
)
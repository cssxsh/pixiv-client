package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
data class CreatorActive(
    @SerialName("creatorId")
    val creatorId: String,
    @SerialName("isActive")
    val isActive: Boolean,
    @SerialName("user")
    val user: CreatorInfo
)
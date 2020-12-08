package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPreview(
    @SerialName("user")
    val user: UserInfo,
    @SerialName("illusts")
    val illusts: List<IllustInfo>,
    @SerialName("novels")
    val novels: List<NovelInfo>,
    @SerialName("is_muted")
    val isMuted: Boolean
)
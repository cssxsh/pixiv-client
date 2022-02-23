package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class UserPreview(
    @SerialName("user")
    val user: UserInfo,
    @SerialName("illusts")
    val illusts: List<IllustInfo>,
    @SerialName("novels")
    val novels: List<NovelInfo>,
    @SerialName("is_muted")
    val isMuted: Boolean,
)
package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserPreview(
    @SerialName("user")
    val user: UserInfo,
    @SerialName("illusts")
    val illusts: List<Illust>,
    @SerialName("novels")
    val novels: List<Novel>,
    @SerialName("is_muted")
    val isMuted: Boolean
)
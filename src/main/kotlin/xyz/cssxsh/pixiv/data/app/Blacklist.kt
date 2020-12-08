package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Blacklist(
    @SerialName("users")
    val users: List<UserInfo>
)
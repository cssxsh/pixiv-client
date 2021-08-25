package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
data class Blacklist(
    @SerialName("users")
    val users: List<UserInfo>,
)
package xyz.cssxsh.pixiv.web

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserFollowing(
    @SerialName("followUserTags")
    val tags: List<String>,
    @SerialName("total")
    val total: Long,
    @SerialName("users")
    val users: List<UserWebPreview>
)
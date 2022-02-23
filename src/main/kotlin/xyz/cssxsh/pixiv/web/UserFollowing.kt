package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
public data class UserFollowing(
    @SerialName("followUserTags")
    val tags: List<String>,
    @SerialName("total")
    val total: Long,
    @SerialName("users")
    val users: List<UserWebPreview>
)
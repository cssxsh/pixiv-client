package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
data class TwitterAccountInfo(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("screenName")
    val screenName: String,
    @SerialName("url")
    val url: String
)
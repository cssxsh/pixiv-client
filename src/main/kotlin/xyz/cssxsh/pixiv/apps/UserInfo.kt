package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*

@Serializable
public data class UserInfo(
    @SerialName("account")
    val account: String,
    @SerialName("id")
    val id: Long,
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("is_followed")
    val isFollowed: Boolean? = null,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image_urls")
    val profileImageUrls: FileUrls,
)
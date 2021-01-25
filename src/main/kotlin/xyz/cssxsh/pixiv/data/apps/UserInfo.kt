package xyz.cssxsh.pixiv.data.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.FileUrls

@Serializable
data class UserInfo(
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
    val profileImageUrls: FileUrls
)
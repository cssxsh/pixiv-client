package xyz.cssxsh.pixiv.client.data.aapi


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.client.data.FileUrls

@Serializable
data class UserInfo(
    @SerialName("account")
    val account: String,
    @SerialName("id")
    val id: Int,
    @SerialName("is_followed")
    val isFollowed: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("profile_image_urls")
    val profileImageUrls: FileUrls
)
package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagInfo(
    @SerialName("name")
    val name: String,
    @SerialName("translated_name")
    val translatedName: String? = null,
    @SerialName("added_by_uploaded_user")
    val addedByUploadedUser: Boolean? = null,
    @SerialName("is_registered")
    val isRegistered: Boolean? = null,
    @SerialName("illust")
    val illust: IllustInfo? = null
)
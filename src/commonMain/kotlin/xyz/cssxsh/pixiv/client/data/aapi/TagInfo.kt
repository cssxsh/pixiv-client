package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagInfo(
    @SerialName("name")
    val name: String,
    @SerialName("translated_name")
    val translatedName: String? = null,
    @SerialName("added_by_uploaded_user")
    val addedByUploadedUser: Boolean? = null
)
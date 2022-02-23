package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class TagInfo(
    @SerialName("name")
    val name: String,
    @SerialName("translated_name")
    val translatedName: String? = null,
    @SerialName("added_by_uploaded_user")
    val addedByUploadedUser: Boolean? = null,
    @SerialName("is_registered")
    val isRegistered: Boolean? = null,
)
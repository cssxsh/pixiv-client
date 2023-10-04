package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
public data class UserPickupInfo(
    @SerialName("contentUrl")
    val contentUrl: String,
    @SerialName("deletable")
    val deletable: Boolean,
    @SerialName("description")
    val description: String,
    @SerialName("draggable")
    val draggable: Boolean,
    @SerialName("hasAdultContent")
    val hasAdultContent: Boolean,
    @SerialName("imageUrl")
    val imageUrl: String?,
    @SerialName("imageUrlMobile")
    val imageUrlMobile: String?,
    @SerialName("type")
    val type: String,
    @SerialName("userImageUrl")
    val userImageUrl: String,
    @SerialName("userName")
    val userName: String
)
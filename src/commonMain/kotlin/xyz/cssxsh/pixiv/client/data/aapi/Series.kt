package xyz.cssxsh.pixiv.client.data.aapi


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Series(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("title")
    val title: String? = null
)
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeriesInfo(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("title")
    val title: String? = null,
)
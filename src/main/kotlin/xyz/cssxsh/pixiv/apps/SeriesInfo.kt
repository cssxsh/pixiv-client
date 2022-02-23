package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class SeriesInfo(
    @SerialName("id")
    val id: Long? = null,
    @SerialName("title")
    val title: String? = null,
)
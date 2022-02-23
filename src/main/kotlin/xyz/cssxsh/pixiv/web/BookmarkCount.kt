package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
public data class BookmarkCount(
    @SerialName("illust")
    val illust: Int,
    @SerialName("novel")
    val novel: Int,
)
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class BookmarkDetailSingle(
    @SerialName("bookmark_detail")
    val detail: BookmarkDetail,
)
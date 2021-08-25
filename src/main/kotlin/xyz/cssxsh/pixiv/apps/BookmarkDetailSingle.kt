package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
data class BookmarkDetailSingle(
    @SerialName("bookmark_detail")
    val detail: BookmarkDetail,
)
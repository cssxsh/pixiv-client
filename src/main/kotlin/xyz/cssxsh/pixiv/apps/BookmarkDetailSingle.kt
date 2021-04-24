package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkDetailSingle(
    @SerialName("bookmark_detail")
    val detail: BookmarkDetail,
)
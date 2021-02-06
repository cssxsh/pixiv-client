package xyz.cssxsh.pixiv.data.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkDetailSingle(
    @SerialName("bookmark_detail")
    val bookmarkDetail: BookmarkDetail,
)
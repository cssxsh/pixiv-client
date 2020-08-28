package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkDetailSingle(
    @SerialName("bookmark_detail")
    val bookmarkDetail: BookmarkDetail
)
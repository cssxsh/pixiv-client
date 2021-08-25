package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*

@Serializable
data class BookmarkDetail(
    @SerialName("is_bookmarked")
    val isBookmarked: Boolean,
    @SerialName("tags")
    val tags: List<TagInfo>,
    @SerialName("restrict")
    val restrict: PublicityType,
)
package xyz.cssxsh.pixiv.data.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.PublicityType

@Serializable
data class BookmarkDetail(
    @SerialName("is_bookmarked")
    val isBookmarked: Boolean,
    @SerialName("tags")
    val tags: List<TagInfo>,
    @SerialName("restrict")
    val restrict: PublicityType
)
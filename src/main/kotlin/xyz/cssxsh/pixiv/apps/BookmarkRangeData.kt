package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
data class BookmarkRangeData(
    @SerialName("bookmark_ranges")
    val ranges: List<BookmarkRange>
) {
    @Serializable
    data class BookmarkRange(
        @SerialName("bookmark_num_max")
        val max: String,
        @SerialName("bookmark_num_min")
        val min: String
    )
}
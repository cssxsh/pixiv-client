package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookmarkTagData(
    @SerialName("bookmark_tags")
    val bookmarkTags: List<BookmarkTag>,
    @SerialName("next_url")
    val nextUrl: String
) {
    @Serializable
    data class BookmarkTag(
        @SerialName("name")
        val name: String,
        @SerialName("count")
        val count: Long
    )
}
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class BookmarkTagData(
    @SerialName("bookmark_tags")
    val tags: List<BookmarkTag>,
    @SerialName("next_url")
    val nextUrl: String?,
) {
    @Serializable
    public data class BookmarkTag(
        @SerialName("name")
        val name: String,
        @SerialName("count")
        val count: Long,
    )
}
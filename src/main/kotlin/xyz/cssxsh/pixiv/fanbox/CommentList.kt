package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
data class CommentList(
    @SerialName("items")
    override val items: List<CommentInfo>,
    @SerialName("nextUrl")
    override val nextUrl: String?
) : ItemList, List<CommentInfo> by items {
    companion object {
        val Empty = CommentList(items = emptyList(), nextUrl = null)
    }
}
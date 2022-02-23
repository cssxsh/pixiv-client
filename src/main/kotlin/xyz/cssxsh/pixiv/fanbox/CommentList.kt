package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
public data class CommentList(
    @SerialName("items")
    override val items: List<CommentInfo>,
    @SerialName("nextUrl")
    override val nextUrl: String?
) : ItemList, List<CommentInfo> by items {
    public companion object {
        public val Empty: CommentList = CommentList(items = emptyList(), nextUrl = null)
    }
}
package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
public data class PostList(
    @SerialName("items")
    override val items: List<PostDetail>,
    @SerialName("nextUrl")
    override val nextUrl: String?,
    val count: Int? = null
) : ItemList, List<PostDetail> by items

package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
data class TagFeature(
    @SerialName("count")
    val count: Int,
    @SerialName("coverImageUrl")
    val coverImageUrl: String,
    @SerialName("tag")
    val tag: String
)
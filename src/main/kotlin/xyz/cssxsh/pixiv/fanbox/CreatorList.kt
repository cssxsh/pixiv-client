package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
data class CreatorList(
    @SerialName("items")
    val items: List<CreatorDetail>,
    @SerialName("nextUrl")
    val nextUrl: String?
)

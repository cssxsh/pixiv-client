package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
data class TagData(
    @SerialName("tags")
    val tags: List<TagInfo>
)
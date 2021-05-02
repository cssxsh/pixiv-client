package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TagData(
    @SerialName("tags")
    val tags: List<TagInfo>
)
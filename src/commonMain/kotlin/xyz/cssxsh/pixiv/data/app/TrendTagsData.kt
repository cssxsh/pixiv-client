package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendTagsData(
    @SerialName("trend_tags")
    val trendTags: List<TagInfo>
)
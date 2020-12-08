package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendTagData(
    @SerialName("trend_tags")
    val trendTags: List<TrendTag>
)
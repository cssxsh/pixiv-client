package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendIllustData(
    @SerialName("trend_tags")
    val trends: List<TrendIllust>,
)
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
data class TrendIllustData(
    @SerialName("trend_tags")
    val trends: List<TrendIllust>,
)
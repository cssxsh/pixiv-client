package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class TrendIllustData(
    @SerialName("trend_tags")
    val trends: List<TrendIllust>,
)
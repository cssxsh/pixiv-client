package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendTagsData(
    @SerialName("trend_tags")
    val trendTags: List<trendTag>
) {
    @Serializable
    data class trendTag(
        @SerialName("tag")
        val tag: String,
        @SerialName("translated_name")
        val translatedName: String? = null,
        @SerialName("illust")
        val illust: Illust
    )
}
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.JapanDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class SpotlightArticle(
    @SerialName("article_url")
    val url: String,
    @SerialName("category")
    val category: String,
    @SerialName("id")
    val aid: Long,
    @SerialName("publish_date")
    @Serializable(with = JapanDateTimeSerializer::class)
    val publish: OffsetDateTime,
    @SerialName("pure_title")
    val pureTitle: String,
    @SerialName("subcategory_label")
    val type: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("title")
    val title: String,
)
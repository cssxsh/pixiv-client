package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.JapanDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class SpotlightArticleData(
    @SerialName("spotlight_articles")
    val articles: List<SpotlightArticle>,
    @SerialName("next_url")
    val nextUrl: String,
) {
    @Serializable
    data class SpotlightArticle(
        @SerialName("article_url")
        val articleUrl: String,
        @SerialName("category")
        val category: String,
        @SerialName("id")
        val id: Long,
        @SerialName("publish_date")
        @Serializable(with = JapanDateTimeSerializer::class)
        val publishDate: OffsetDateTime,
        @SerialName("pure_title")
        val pureTitle: String,
        @SerialName("subcategory_label")
        val subcategoryLabel: String,
        @SerialName("thumbnail")
        val thumbnail: String,
        @SerialName("title")
        val title: String,
    )
}
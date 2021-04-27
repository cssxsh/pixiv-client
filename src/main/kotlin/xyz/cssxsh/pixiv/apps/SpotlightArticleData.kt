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
)
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class SpotlightArticleData(
    @SerialName("spotlight_articles")
    val articles: List<SpotlightArticle>,
    @SerialName("next_url")
    val nextUrl: String?,
)
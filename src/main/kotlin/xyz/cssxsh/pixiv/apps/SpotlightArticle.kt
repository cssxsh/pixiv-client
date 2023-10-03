package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
public data class SpotlightArticle(
    @SerialName("article_url")
    val url: String,
    @SerialName("category")
    val category: String,
    @SerialName("id")
    val aid: Long,
    @SerialName("publish_date")

    val publish: String,
    @SerialName("pure_title")
    val pureTitle: String,
    @SerialName("subcategory_label")
    val type: String,
    @SerialName("thumbnail")
    val thumbnail: String,
    @SerialName("title")
    val title: String,
)
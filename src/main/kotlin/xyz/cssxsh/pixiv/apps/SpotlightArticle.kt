package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import java.time.*

@Serializable
public data class SpotlightArticle(
    @SerialName("article_url")
    val url: String,
    @SerialName("category")
    val category: String,
    @SerialName("id")
    val aid: Long,
    @SerialName("publish_date")
    @Contextual
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
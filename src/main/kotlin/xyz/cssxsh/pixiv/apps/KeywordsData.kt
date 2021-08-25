package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
data class KeywordsData(
    @SerialName("search_auto_complete_keywords")
    val keywords: List<String>,
)
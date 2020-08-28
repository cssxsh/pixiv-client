package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeywordsData(
    @SerialName("search_auto_complete_keywords")
    val keywords: List<String>
)
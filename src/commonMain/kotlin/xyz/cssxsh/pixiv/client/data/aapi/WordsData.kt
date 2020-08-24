package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WordsData(
    @SerialName("search_auto_complete_keywords")
    val keywords: List<String>
)
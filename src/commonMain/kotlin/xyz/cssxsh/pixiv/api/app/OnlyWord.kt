package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlyWord(
    @SerialName("word")
    val word: String
)
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiDefinitionData(
    @SerialName("emoji_definitions")
    val emoticons: List<EmojiDefinition>
) {
    @Serializable
    data class EmojiDefinition(
        @SerialName("id")
        val id: Int,
        @SerialName("image_url_medium")
        val imageUrlMedium: String,
        @SerialName("slug")
        val slug: String
    )
}
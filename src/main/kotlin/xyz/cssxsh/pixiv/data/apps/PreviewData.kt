package xyz.cssxsh.pixiv.data.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviewData(
    @SerialName("user_previews")
    val userPreviews: List<UserPreview>,
    @SerialName("next_url")
    val nextUrl: String?
)
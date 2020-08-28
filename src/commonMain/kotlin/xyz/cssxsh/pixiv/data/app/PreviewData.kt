package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviewData(
    @SerialName("user_previews")
    val UserPreviews: List<UserPreview>,
    @SerialName("next_url")
    val nextUrl: String?
)
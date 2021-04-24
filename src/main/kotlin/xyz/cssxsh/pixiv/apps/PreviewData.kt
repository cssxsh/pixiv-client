package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviewData(
    @SerialName("user_previews")
    val previews: List<UserPreview>,
    @SerialName("next_url")
    val nextUrl: String?,
)
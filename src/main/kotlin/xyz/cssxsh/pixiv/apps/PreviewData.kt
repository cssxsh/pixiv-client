package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
data class PreviewData(
    @SerialName("user_previews")
    val previews: List<UserPreview>,
    @SerialName("next_url")
    val nextUrl: String?,
)
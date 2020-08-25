package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendedInfo(
    @SerialName("user_previews")
    val UserPreviews: List<UserPreview>,
    @SerialName("next_url")
    val nextUrl: String?
)
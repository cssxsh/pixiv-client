package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UgoiraInfo(
    @SerialName("ugoira_metadata")
    val ugoira: UgoiraMetadata,
)
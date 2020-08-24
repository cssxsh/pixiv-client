package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.client.data.Metadata

@Serializable
data class UgoiraMetadata(
    @SerialName("ugoira_metadata")
    val ugoiraMetadata: Metadata
)
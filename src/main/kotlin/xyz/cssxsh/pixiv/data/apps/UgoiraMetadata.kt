package xyz.cssxsh.pixiv.data.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.data.Metadata

@Serializable
data class UgoiraMetadata(
    @SerialName("ugoira_metadata")
    val ugoiraMetadata: Metadata,
)
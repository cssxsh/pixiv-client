package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class UgoiraInfo(
    @SerialName("ugoira_metadata")
    val ugoira: UgoiraMetadata,
)
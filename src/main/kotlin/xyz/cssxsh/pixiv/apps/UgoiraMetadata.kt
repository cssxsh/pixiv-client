package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*

@Serializable
public data class UgoiraMetadata(
    @SerialName("frames")
    val frames: List<UgoiraFrame>,
    @SerialName("zip_urls")
    val zipUrls: FileUrls,
)

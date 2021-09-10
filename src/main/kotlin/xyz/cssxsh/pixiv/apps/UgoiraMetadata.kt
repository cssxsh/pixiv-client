package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*

@Serializable
data class UgoiraMetadata(
    @SerialName("frames")
    val frames: List<Frame>,
    @SerialName("zip_urls")
    val zipUrls: FileUrls,
) {
    @Serializable
    data class Frame(
        @SerialName("delay")
        val delay: Long,
        @SerialName("file")
        val file: String,
    )
}

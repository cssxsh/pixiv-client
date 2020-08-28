package xyz.cssxsh.pixiv.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.FileUrls

@Serializable
data class Metadata(
    @SerialName("frames")
    val frames: List<Frame>,
    @SerialName("zip_urls")
    val zipUrls: FileUrls
) {
    @Serializable
    data class Frame(
        @SerialName("delay")
        val delay: Int,
        @SerialName("file")
        val fileUrl: String
    )
}

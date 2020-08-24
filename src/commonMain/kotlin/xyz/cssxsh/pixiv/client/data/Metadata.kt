package xyz.cssxsh.pixiv.client.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
        val `file`: String
    )
}
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*

@Serializable
data class UgoiraFrame(
    @SerialName("delay")
    val delay: Long,
    @SerialName("file")
    val file: String,
)

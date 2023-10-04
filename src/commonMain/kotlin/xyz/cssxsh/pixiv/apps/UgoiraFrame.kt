package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class UgoiraFrame(
    @SerialName("delay")
    val delay: Long,
    @SerialName("file")
    val file: String,
)

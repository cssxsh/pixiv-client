package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class IllustSingle(
    @SerialName("illust")
    val illust: IllustInfo,
)
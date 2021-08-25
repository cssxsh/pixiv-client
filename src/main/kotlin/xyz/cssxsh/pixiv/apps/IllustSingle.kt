package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
data class IllustSingle(
    @SerialName("illust")
    val illust: IllustInfo,
)
package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class TrendIllust(
    @SerialName("translated_name")
    val translatedName: String? = null,
    @SerialName("tag")
    val tag: String,
    @SerialName("illust")
    val illust: IllustInfo,
)
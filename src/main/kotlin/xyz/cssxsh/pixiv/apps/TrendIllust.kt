package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendIllust(
    @SerialName("translated_name")
    val translatedName: String? = null,
    @SerialName("tag")
    val tag: String,
    @SerialName("illust")
    val illust: IllustInfo,
)
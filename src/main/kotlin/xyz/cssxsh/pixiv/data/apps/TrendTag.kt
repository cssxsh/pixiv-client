package xyz.cssxsh.pixiv.data.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendTag(
    @SerialName("translated_name")
    val translatedName: String? = null,
    @SerialName("tag")
    val tag: String,
    @SerialName("illust")
    val illust: IllustInfo,
)
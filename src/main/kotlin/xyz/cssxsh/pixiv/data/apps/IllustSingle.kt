package xyz.cssxsh.pixiv.data.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IllustSingle(
    @SerialName("illust")
    val illust: IllustInfo,
)
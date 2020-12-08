package xyz.cssxsh.pixiv.data.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IllustSingle(
    @SerialName("illust")
    val illust: IllustInfo
)
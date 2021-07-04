package xyz.cssxsh.pixiv.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeiboQrcode(
    @SerialName("url")
    val url: String,
    @SerialName("vcode")
    val vcode: String
)

@Serializable
data class WeiboQrcodeStatus(
    @SerialName("status")
    val code: Int,
    @SerialName("url")
    val url: String? = null
)
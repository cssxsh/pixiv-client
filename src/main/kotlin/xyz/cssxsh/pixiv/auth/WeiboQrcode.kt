package xyz.cssxsh.pixiv.auth

import kotlinx.serialization.*

@Serializable
data class WeiboQrcode(
    @SerialName("url")
    val url: String,
    @SerialName("vcode")
    val vcode: String
)
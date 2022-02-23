package xyz.cssxsh.pixiv.auth

import kotlinx.serialization.*

@Serializable
public data class WeiboQrcodeStatus(
    @SerialName("status")
    val code: Int,
    @SerialName("url")
    val url: String? = null
)
package xyz.cssxsh.pixiv

import kotlinx.serialization.*

@Serializable
public data class PixivConfig(
    @SerialName("headers")
    var headers: HeadersMap = ANDROID_HEADERS,
    @SerialName("proxy")
    var proxy: String = "",
    @SerialName("dns")
    var dns: String = JAPAN_DNS,
    @SerialName("host")
    var host: Map<String, List<String>> = DEFAULT_PIXIV_HOST,
    @SerialName("sni")
    var sni: Boolean = true,
    @SerialName("refresh_token")
    var refreshToken: String? = null,
)
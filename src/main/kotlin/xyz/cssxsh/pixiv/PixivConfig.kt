package xyz.cssxsh.pixiv

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PixivConfig(
    @SerialName("headers")
    var headers: HeadersMap = IOS_HEADERS,
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
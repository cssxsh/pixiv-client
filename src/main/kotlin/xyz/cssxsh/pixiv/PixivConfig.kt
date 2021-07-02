package xyz.cssxsh.pixiv

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PixivConfig(
    @SerialName("headers")
    var headers: HeadersMap = IOS_HEADERS,
    @SerialName("proxy")
    var proxy: String? = null,
    @SerialName("dns")
    var dns: String = JAPAN_DNS,
    @SerialName("host")
    var host: Map<String, List<String>> = emptyMap(),
    @SerialName("cname")
    var cname: Map<String, String> = PIXIV_CNAME,
    @SerialName("ruby_ssl_factory")
    var useRubySSLFactory: Boolean = true,
    @SerialName("refresh_token")
    var refreshToken: String? = null,
)
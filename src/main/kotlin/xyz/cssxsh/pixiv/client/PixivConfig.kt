@file:Suppress("unused")

package xyz.cssxsh.pixiv.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.*

@Serializable
data class PixivConfig(
    @SerialName("client")
    var client: Client = Client(),
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
    @SerialName("account")
    var account: Account? = null,
    @SerialName("refresh_token")
    var refreshToken: String? = null,
) {

    @Serializable
    data class Client(
        @SerialName("id")
        var id: String = CLIENT_ID,
        @SerialName("secret")
        var secret: String = CLIENT_SECRET,
        @SerialName("hash_secret")
        var hashSecret: String = HASH_SECRET,
    )

    @Serializable
    data class Account(
        @SerialName("mail_or_uid")
        var mailOrUID: String,
        @SerialName("password")
        var password: String,
    )
}
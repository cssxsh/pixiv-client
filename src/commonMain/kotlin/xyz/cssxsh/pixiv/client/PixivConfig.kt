@file:Suppress("unused")

package xyz.cssxsh.pixiv.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.HeadersMap

@Serializable
data class PixivConfig(
    @SerialName("client")
    var client: Client = Client(),
    @SerialName("auth")
    var auth: Auth = Auth(),
    @SerialName("headers")
    var headers: HeadersMap = Util.IOS_HEADERS,
    @SerialName("proxy")
    var proxy: String? = null,
    @SerialName("dns")
    var dns: String = "https://1.0.0.1/dns-query",
    @SerialName("cname")
    var cname: Map<String, String> = mapOf(
        "oauth.secure.pixiv.net" to "api.fanbox.cc",
        "app-api.pixiv.net" to "api.fanbox.cc",
        "public.pixiv.net" to "api.fanbox.cc"
    ),
    @SerialName("ruby_ssl_factory")
    var RubySSLFactory: Boolean = true,
    @SerialName("account")
    var account: Account? = null,
    @SerialName("refresh_token")
    var refreshToken: String? = null
) {

    fun client(block: Client.() -> Unit) {
        client = client.apply(block)
    }

    fun auth(block: Auth.() -> Unit) {
        auth = auth.apply(block)
    }

    fun account(block: Account.() -> Unit) {
        account = account?.apply(block) ?: Account("", "").apply(block)
    }

    @Serializable
    data class Client(
        @SerialName("id")
        var id: String = Util.CLIENT_ID,
        @SerialName("secret")
        var secret: String = Util.CLIENT_SECRET,
        @SerialName("hash_secret")
        var hashSecret: String = Util.HASH_SECRET
    )

    @Serializable
    data class Auth(
        @SerialName("url")
        var url: String = Util.OAUTH_URL
    )

    @Serializable
    data class Account(
        @SerialName("mail_or_uid")
        var mailOrUID: String,
        @SerialName("password")
        var password: String
    )
}
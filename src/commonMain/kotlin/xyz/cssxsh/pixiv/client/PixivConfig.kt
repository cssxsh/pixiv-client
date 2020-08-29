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
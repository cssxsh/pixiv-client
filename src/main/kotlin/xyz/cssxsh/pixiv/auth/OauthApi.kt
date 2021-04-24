package xyz.cssxsh.pixiv.auth

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okio.ByteString.Companion.encode
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.JapanDateTimeSerializer
import java.time.OffsetDateTime

const val OAUTH_URL: String = "https://oauth.secure.pixiv.net/auth/token"

@Serializable
data class ClientConfig(
    @SerialName("id")
    var id: String = CLIENT_ID,
    @SerialName("secret")
    var secret: String = CLIENT_SECRET,
    @SerialName("hash_secret")
    var hashSecret: String = HASH_SECRET,
)

enum class GrantType : PixivParam {
    PASSWORD,
    REFRESH_TOKEN;
}

@Serializable
data class Account(
    @SerialName("mail_or_uid")
    var mailOrUID: String,
    @SerialName("password")
    var password: String,
)

suspend fun UseHttpClient.oauth(
    client: ClientConfig,
    grant: GrantType,
    account: Account? = null,
    refresh: String? = null,
    time: OffsetDateTime,
): AuthResult = useHttpClient {
    it.post(OAUTH_URL) {
        attributes.put(PixivAccessToken.PixivAuthMark, Unit)
        time.format(JapanDateTimeSerializer.dateFormat).let { time ->
            header("X-Client-Hash", (time + client).encode().md5())
            header("X-Client-Time", time)
        }
        body = FormDataContent(Parameters.build {
            append("get_secure_url", "1")
            append("client_id", client.id)
            append("client_secret", client.secret)
            append("grant_type", grant.value())
            when (grant) {
                GrantType.PASSWORD -> requireNotNull(account) { "账户为空" }.let { account ->
                    append("username", account.mailOrUID)
                    append("password", account.password)
                }
                GrantType.REFRESH_TOKEN -> requireNotNull(refresh) { "Token为空" }.let { taken ->
                    append("refresh_token", taken)
                }
            }
        })
    }
}
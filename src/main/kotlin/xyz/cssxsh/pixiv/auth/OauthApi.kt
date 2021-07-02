package xyz.cssxsh.pixiv.auth

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import okio.ByteString.Companion.encode
import xyz.cssxsh.pixiv.*
import java.time.OffsetDateTime

const val OAUTH_TOKEN_URL = "https://oauth.secure.pixiv.net/auth/token"

const val LOGIN_URL = "https://app-api.pixiv.net/web/v1/login"

const val START_URL = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/start"

const val REDIRECT_URL = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback"

const val REDIRECT_END_URL = "https://accounts.pixiv.net/post-redirect"

enum class GrantType : PixivParam {
    AUTHORIZATION_CODE,
    REFRESH_TOKEN;
}

fun verifier(client: String = "pixiv-android", time: OffsetDateTime = OffsetDateTime.now()): Pair<String, Url> {
    val origin = time.toString().encode().sha512().base64Url().replace("=", "")

    return origin to Url(LOGIN_URL).copy(parameters = Parameters.build {
        append("code_challenge", origin.encode().sha256().base64Url().replace("=", ""))
        append("code_challenge_method", "S256")
        append("client", client)
    })
}

suspend fun UseHttpClient.authorize(code: String, verifier: String): AuthResult = useHttpClient {
    it.post(OAUTH_TOKEN_URL) {
        attributes.put(PixivAccessToken.PixivAuthMark, Unit)
        body = FormDataContent(Parameters.build {
            append("client_id", CLIENT_ID)
            append("client_secret", CLIENT_SECRET)
            append("grant_type", GrantType.AUTHORIZATION_CODE.value())
            append("include_policy", "true")

            append("code", code)
            append("code_verifier", verifier)
            append("redirect_uri", REDIRECT_URL)
        })
    }
}

suspend fun UseHttpClient.refresh(token: String): AuthResult = useHttpClient {
    it.post(OAUTH_TOKEN_URL) {
        attributes.put(PixivAccessToken.PixivAuthMark, Unit)
        body = FormDataContent(Parameters.build {
            append("client_id", CLIENT_ID)
            append("client_secret", CLIENT_SECRET)
            append("grant_type", GrantType.REFRESH_TOKEN.value())
            append("include_policy", "true")

            append("refresh_token", token)
        })
    }
}
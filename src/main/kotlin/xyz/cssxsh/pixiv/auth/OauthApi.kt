package xyz.cssxsh.pixiv.auth

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import okio.ByteString.Companion.encode
import xyz.cssxsh.pixiv.*
import java.time.*

const val OAUTH_TOKEN_URL = "https://oauth.secure.pixiv.net/auth/token"

const val OAUTH_AUTHORIZE_URL = "https://oauth.secure.pixiv.net/auth/authorize"

const val REDIRECT_LOGIN_URL = "https://app-api.pixiv.net/web/v1/login"

const val START_URL = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/start"

const val REDIRECT_URL = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback"

const val ORIGIN_URL = "https://accounts.pixiv.net"

const val LOGIN_URL = "https://accounts.pixiv.net/login"

const val POST_SELECTED_URL = "https://accounts.pixiv.net/account-selected"

const val POST_REDIRECT_URL = "https://accounts.pixiv.net/post-redirect"

fun verifier(time: OffsetDateTime = OffsetDateTime.now()): Pair<String, Url> {
    val origin = time.toString().encode().sha512().base64Url().replace("=", "")

    return origin to Url(REDIRECT_LOGIN_URL).copy(parameters = Parameters.build {
        append("code_challenge", origin.encode().sha256().base64Url().replace("=", ""))
        append("code_challenge_method", "S256")
        append("client", "pixiv-android")
    })
}

suspend fun UseHttpClient.authorize(code: String, verifier: String): AuthResult = useHttpClient {
    it.post(OAUTH_TOKEN_URL) {
        body = FormDataContent(Parameters.build {
            append("client_id", CLIENT_ID)
            append("client_secret", CLIENT_SECRET)
            append("grant_type", "authorization_code")
            append("include_policy", "true")

            append("code", code)
            append("code_verifier", verifier)
            append("redirect_uri", REDIRECT_URL)
        })
    }
}

suspend fun UseHttpClient.refresh(token: String): AuthResult = useHttpClient {
    it.post(OAUTH_TOKEN_URL) {
        body = FormDataContent(Parameters.build {
            append("client_id", CLIENT_ID)
            append("client_secret", CLIENT_SECRET)
            append("grant_type", "refresh_token")
            append("include_policy", "true")

            append("refresh_token", token)
        })
    }
}
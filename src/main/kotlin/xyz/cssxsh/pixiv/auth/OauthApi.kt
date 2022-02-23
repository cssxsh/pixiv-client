package xyz.cssxsh.pixiv.auth

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import okio.ByteString.Companion.encode
import xyz.cssxsh.pixiv.*
import java.time.*

public const val OAUTH_TOKEN_URL: String = "https://oauth.secure.pixiv.net/auth/token"

public const val OAUTH_AUTHORIZE_URL: String = "https://oauth.secure.pixiv.net/auth/authorize"

public const val REDIRECT_LOGIN_URL: String = "https://app-api.pixiv.net/web/v1/login"

public const val START_URL: String = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/start"

public const val REDIRECT_URL: String = "https://app-api.pixiv.net/web/v1/users/auth/pixiv/callback"

public const val ORIGIN_URL: String = "https://accounts.pixiv.net"

public const val LOGIN_URL: String = "https://accounts.pixiv.net/login"

public const val LOGIN_API_URL: String = "https://accounts.pixiv.net/api/login"

public const val POST_SELECTED_URL: String = "https://accounts.pixiv.net/account-selected"

public const val POST_REDIRECT_URL: String = "https://accounts.pixiv.net/post-redirect"

internal fun verifier(time: OffsetDateTime): Pair<String, Url> {
    val origin = time.toString().encode().sha512().base64Url().replace("=", "")

    return origin to Url(REDIRECT_LOGIN_URL).copy(parameters = Parameters.build {
        append("code_challenge", origin.encode().sha256().base64Url().replace("=", ""))
        append("code_challenge_method", "S256")
        append("client", "pixiv-android")
    })
}

internal suspend fun UseHttpClient.authorize(code: String, verifier: String): AuthResult = useHttpClient {
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

internal suspend fun UseHttpClient.refresh(token: String): AuthResult = useHttpClient {
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
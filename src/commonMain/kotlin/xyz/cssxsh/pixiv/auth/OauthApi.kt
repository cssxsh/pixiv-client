package xyz.cssxsh.pixiv.auth

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import org.kotlincrypto.hash.sha2.SHA256
import xyz.cssxsh.pixiv.ANDROID_CLIENT_ID
import xyz.cssxsh.pixiv.ANDROID_CLIENT_SECRET
import kotlin.random.Random

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

public const val GIGYA_AUTH_URL: String = "https://accounts.pixiv.net/gigya-auth"

public const val SOCIALIZE_LOGIN_URL: String = "https://socialize.gigya.com/socialize.login"

private fun String.sha256(): ByteArray {
    val digest = SHA256()
    return digest.digest(toByteArray(Charsets.UTF_8))
}

private const val VERIFIER_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~"

private const val VERIFIER_LENGTH = 128

internal fun verifier(time: Long): Pair<String, Parameters> {
    val random = Random(time)
    val origin = buildString(VERIFIER_LENGTH) {
        repeat(VERIFIER_LENGTH) {
            append(VERIFIER_CHARS[random.nextInt(VERIFIER_CHARS.length)])
        }
    }
    val challenge = origin
        .sha256()
        .encodeBase64()
        .replace("=", "")
    val parameters = ParametersBuilder().apply {
        append("code_challenge", challenge)
        append("code_challenge_method", "S256")
        append("client", "pixiv-android")
    }.build()

    return origin to parameters
}

internal suspend fun HttpClient.authorize(code: String, verifier: String): AuthResult {
    return submitForm(url = OAUTH_TOKEN_URL, formParameters = Parameters.build {
        append("client_id", ANDROID_CLIENT_ID)
        append("client_secret", ANDROID_CLIENT_SECRET)
        append("grant_type", "authorization_code")
        append("include_policy", "true")
        append("code", code)
        append("code_verifier", verifier)
        append("redirect_uri", REDIRECT_URL)
    }).body()
}

internal suspend fun HttpClient.refresh(token: String): AuthResult {
    return submitForm(url = OAUTH_TOKEN_URL, formParameters = Parameters.build {
        append("client_id", ANDROID_CLIENT_ID)
        append("client_secret", ANDROID_CLIENT_SECRET)
        append("grant_type", "refresh_token")
        append("include_policy", "true")
        append("refresh_token", token)
    }).body()
}
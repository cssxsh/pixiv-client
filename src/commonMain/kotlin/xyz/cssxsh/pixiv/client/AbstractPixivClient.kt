package xyz.cssxsh.pixiv.client

import com.soywiz.klock.wrapped.WDateTime
import com.soywiz.krypto.md5
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.forms.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.GrantType
import xyz.cssxsh.pixiv.client.exception.NotLoginException
import xyz.cssxsh.pixiv.data.AuthResult

abstract class AbstractPixivClient : PixivClient {

    private var authResult: AuthResult? = null

    override val authInfo: AuthResult.AuthInfo
        get() = authResult?.info ?: throw NotLoginException()

    override val isLoggedIn: Boolean
        get() = authResult != null

    abstract override var httpClient: HttpClient

    open suspend fun login(mailOrPixivID: String, password: String): AuthResult.AuthInfo {
        config { account = PixivConfig.Account(mailOrPixivID, password) }
        return login()
    }

    open suspend fun refresh(token: String): AuthResult.AuthInfo {
        config { refreshToken = token }
        return refresh()
    }

    override suspend fun auth(grantType: GrantType): AuthResult.AuthInfo =
        httpClient.post<AuthResult>(config.auth.url) {
            WDateTime.now().format("yyyy-MM-dd'T'HH:mm:ssXXX").let {
                header("X-Client-Hash", (it + config.client.hashSecret).encodeToByteArray().md5().hex)
                header("X-Client-Time", it)
            }
            body = FormDataContent(Parameters.build {
                append("get_secure_url", "1")
                append("client_id", config.client.id)
                append("client_secret", config.client.secret)
                append("grant_type", grantType.value())
                when (grantType) {
                    GrantType.PASSWORD -> {
                        config.account?.let {
                            append("username", it.mailOrUID)
                            append("password", it.password)
                        } ?: throw IllegalArgumentException("账户为空")
                    }
                    GrantType.REFRESH_TOKEN -> {
                        config.refreshToken?.let {
                            append("refresh_token", it)
                        } ?: throw IllegalArgumentException("Token为空")
                    }
                }
            })
        }.also {
            authResult = it
            httpClient = httpClient.config { defaultRequest { headers["Authorization"] = "Bearer ${it.accessToken}" } }
        }.info
}
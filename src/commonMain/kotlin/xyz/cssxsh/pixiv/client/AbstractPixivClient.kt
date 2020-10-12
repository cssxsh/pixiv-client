package xyz.cssxsh.pixiv.client

import com.soywiz.klock.seconds
import com.soywiz.klock.wrapped.WDateTime
import com.soywiz.klock.wrapped.wrapped
import com.soywiz.krypto.md5
import io.ktor.client.request.forms.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.GrantType
import xyz.cssxsh.pixiv.api.OauthApi
import xyz.cssxsh.pixiv.data.AuthResult
import xyz.cssxsh.pixiv.useHttpClient

abstract class AbstractPixivClient : PixivClient {

    protected open var authInfo: AuthResult.AuthInfo? = null

    override suspend fun getAuthInfo(): AuthResult.AuthInfo {
        if (expiresTime > WDateTime.now()) authInfo = null
        return authInfo ?: autoAuth()
    }

    private var expiresTime: WDateTime = WDateTime.now()

    override suspend fun autoAuth(): AuthResult.AuthInfo = config.run {
        refreshToken?.let { token ->
            refresh(token)
        } ?: account?.let { account ->
            login(account.mailOrUID, account.password)
        } ?: throw IllegalArgumentException("没有登陆参数")
    }

    override suspend fun login(mailOrPixivID: String, password: String): AuthResult.AuthInfo = auth(GrantType.PASSWORD, config {
        account = PixivConfig.Account(mailOrPixivID, password)
    })

    open suspend fun login(): AuthResult.AuthInfo = auth(GrantType.PASSWORD, config)

    override suspend fun refresh(token: String): AuthResult.AuthInfo = auth(GrantType.REFRESH_TOKEN, config {
        refreshToken = token
    })

    open suspend fun refresh(): AuthResult.AuthInfo = auth(GrantType.REFRESH_TOKEN, config)

    override suspend fun auth(grantType: GrantType, config: PixivConfig) = auth(grantType, config, OauthApi.OAUTH_URL)

    suspend fun auth(grantType: GrantType, config: PixivConfig, url: String): AuthResult.AuthInfo = useHttpClient { client ->
        client.post<AuthResult>(url) {
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
                    GrantType.PASSWORD -> requireNotNull(config.account) { "账户为空" }.let {
                        append("username", it.mailOrUID)
                        append("password", it.password)
                    }
                    GrantType.REFRESH_TOKEN -> requireNotNull(config.refreshToken) { "Token为空" }.let {
                        append("refresh_token", it)
                    }
                }
            })
        }
    }.also {
        expiresTime = WDateTime.now() + it.info.expiresIn.seconds.wrapped
        authInfo = it.info
    }.info
}
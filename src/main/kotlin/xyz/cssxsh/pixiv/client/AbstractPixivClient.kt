package xyz.cssxsh.pixiv.client

import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.GrantType
import xyz.cssxsh.pixiv.api.OauthApi
import xyz.cssxsh.pixiv.client.PixivAccessToken.Companion.PixivAuthMark
import xyz.cssxsh.pixiv.data.AuthResult
import xyz.cssxsh.pixiv.data.JapanDateTimeSerializer
import java.security.MessageDigest
import java.time.OffsetDateTime
import java.time.Duration

abstract class AbstractPixivClient : PixivClient {

    protected open var authInfo: AuthResult.AuthInfo? = null

    protected open var expiresTime: OffsetDateTime = OffsetDateTime.now().withNano(0)

    protected abstract val apiIgnore: suspend (Throwable) -> Boolean

    protected abstract fun httpClient(): HttpClient

    protected open suspend fun <R> useHttpClient(
        ignore: suspend (Throwable) -> Boolean,
        block: suspend PixivClient.(HttpClient) -> R,
    ): R = httpClient().use { client ->
        runCatching {
            block(client)
        }.getOrElse { throwable ->
            if (ignore(throwable)) {
                useHttpClient(ignore = ignore, block = block)
            } else {
                throw throwable
            }
        }
    }

    override suspend fun <R> useHttpClient(block: suspend PixivClient.(HttpClient) -> R): R =
        useHttpClient(ignore = apiIgnore, block)

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
            attributes.put(PixivAuthMark, Unit)
            OffsetDateTime.now().format(JapanDateTimeSerializer.dateFormat).let { time ->
                header("X-Client-Hash", (time + config.client.hashSecret).encodeToByteArray().let { data ->
                    MessageDigest.getInstance("md5").digest(data).asUByteArray().joinToString("") {
                        """%02x""".format(it.toInt())
                    }
                })
                header("X-Client-Time", time)
            }
            body = FormDataContent(Parameters.build {
                append("get_secure_url", "1")
                append("client_id", config.client.id)
                append("client_secret", config.client.secret)
                append("grant_type", grantType.value())
                when (grantType) {
                    GrantType.PASSWORD -> requireNotNull(config.account) { "账户为空" }.let { account ->
                        append("username", account.mailOrUID)
                        append("password", account.password)
                    }
                    GrantType.REFRESH_TOKEN -> requireNotNull(config.refreshToken) { "Token为空" }.let { taken ->
                        append("refresh_token", taken)
                    }
                }
            })
        }.info
    }.also {
        synchronized(expiresTime) {
            expiresTime = OffsetDateTime.now().withNano(0) + Duration.ofSeconds(it.expiresIn - 10)
            authInfo = it
        }
        config {
            refreshToken = it.refreshToken
        }
    }
}
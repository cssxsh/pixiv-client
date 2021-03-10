package xyz.cssxsh.pixiv.client

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.sync.withLock
import okio.ByteString.Companion.encode
import xyz.cssxsh.pixiv.GrantType
import xyz.cssxsh.pixiv.api.OauthApi
import xyz.cssxsh.pixiv.data.AuthResult
import xyz.cssxsh.pixiv.data.JapanDateTimeSerializer
import java.time.OffsetDateTime
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class SimplePixivClient(
    parentCoroutineContext: CoroutineContext = EmptyCoroutineContext,
    coroutineName: String = "SimplePixivClient",
    override val config: PixivConfig,
) : PixivClient, AbstractPixivClient() {

    constructor(
        parentCoroutineContext: CoroutineContext = EmptyCoroutineContext,
        coroutineName: String = "SimplePixivClient",
        block: PixivConfig.() -> Unit,
    ) : this(parentCoroutineContext, coroutineName, PixivConfig().apply(block))

    override val coroutineContext: CoroutineContext by lazy {
        parentCoroutineContext + CoroutineName(coroutineName)
    }

    override val apiIgnore: suspend (Throwable) -> Boolean = { true }

    override suspend fun autoAuth(): AuthResult = config.run {
        refreshToken?.let { token ->
            refresh(token)
        } ?: account?.let { account ->
            login(account.mailOrUID, account.password)
        } ?: throw IllegalArgumentException("没有登陆参数")
    }

    override suspend fun getAuthInfo(): AuthResult = mutex.withLock {
        authInfo?.takeIf { expiresTime > OffsetDateTime.now() } ?: autoAuth()
    }

    override suspend fun login(mailOrPixivID: String, password: String): AuthResult = auth(GrantType.PASSWORD, config {
        account = PixivConfig.Account(mailOrPixivID, password)
    })

    open suspend fun login(): AuthResult = auth(GrantType.PASSWORD, config)

    override suspend fun refresh(token: String): AuthResult = auth(GrantType.REFRESH_TOKEN, config {
        refreshToken = token
    })

    open suspend fun refresh(): AuthResult = auth(GrantType.REFRESH_TOKEN, config)

    override suspend fun auth(grantType: GrantType, config: PixivConfig) = auth(grantType, config, OauthApi.OAUTH_URL)

    @Suppress("unused")
    suspend fun auth(grantType: GrantType, config: PixivConfig, url: String): AuthResult = mutex.withLock {
        useHttpClient { client ->
            client.post<AuthResult>(url) {
                attributes.put(PixivAccessToken.PixivAuthMark, Unit)
                OffsetDateTime.now().format(JapanDateTimeSerializer.dateFormat).let { time ->
                    header("X-Client-Hash", (time + config.client.hashSecret).encode().md5())
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
            }
        }.also {
            expiresTime = OffsetDateTime.now().withNano(0).plusSeconds(it.expiresIn)
            authInfo = it
            config {
                refreshToken = it.refreshToken
            }
        }
    }
}
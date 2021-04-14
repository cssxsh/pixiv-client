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

    protected open suspend fun auto() = config.run {
        refreshToken?.let {
            auth(grantType = GrantType.REFRESH_TOKEN, config = this, time = OffsetDateTime.now())
        } ?: account?.let {
            auth(grantType = GrantType.PASSWORD, config = this, time = OffsetDateTime.now())
        } ?: throw IllegalArgumentException("没有登陆参数")
    }

    override suspend fun autoAuth(): AuthResult = mutex.withLock {
        auto()
    }

    override suspend fun getAuthInfo(): AuthResult = mutex.withLock {
        authInfo?.takeIf { expiresTime > OffsetDateTime.now() } ?: auto()
    }

    override suspend fun login(mailOrPixivID: String, password: String): AuthResult = auth(GrantType.PASSWORD, config {
        account = PixivConfig.Account(mailOrPixivID, password)
    }, OffsetDateTime.now())

    override suspend fun refresh(token: String): AuthResult = auth(GrantType.REFRESH_TOKEN, config {
        refreshToken = token
    }, OffsetDateTime.now())

    protected open suspend fun auth(grantType: GrantType, config: PixivConfig, time: OffsetDateTime): AuthResult {
        return useHttpClient { client ->
            client.post<AuthResult>(OauthApi.OAUTH_URL) {
                attributes.put(PixivAccessToken.PixivAuthMark, Unit)
                time.format(JapanDateTimeSerializer.dateFormat).let { time ->
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
            expiresTime = time.withNano(0).plusSeconds(it.expiresIn)
            authInfo = it
            config {
                refreshToken = it.refreshToken
            }
        }
    }
}
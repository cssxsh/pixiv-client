package xyz.cssxsh.pixiv

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.sync.withLock
import xyz.cssxsh.pixiv.auth.*
import java.time.OffsetDateTime
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class SimplePixivClient(
    parentCoroutineContext: CoroutineContext = EmptyCoroutineContext,
    coroutineName: String = "SimplePixivClient",
    override val config: PixivConfig,
) : AutoPixivClient(), PixivWebClient {

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
            auth(grant = GrantType.REFRESH_TOKEN, config = this, time = OffsetDateTime.now())
        } ?: account?.let {
            auth(grant = GrantType.PASSWORD, config = this, time = OffsetDateTime.now())
        } ?: throw IllegalArgumentException("没有登陆参数")
    }

    override suspend fun autoAuth(): AuthResult = mutex.withLock {
        auto()
    }

    override suspend fun getAuthInfo(): AuthResult = mutex.withLock {
        authInfo?.takeIf { expiresTime > OffsetDateTime.now() } ?: auto()
    }

    override suspend fun login(mailOrPixivID: String, password: String): AuthResult = auth(
        grant = GrantType.PASSWORD,
        config = config { account = Account(mailOrPixivID, password) },
        time = OffsetDateTime.now()
    )

    override suspend fun refresh(token: String): AuthResult = auth(
        grant = GrantType.REFRESH_TOKEN,
        config = config { refreshToken = token },
        time = OffsetDateTime.now()
    )

    protected open suspend fun auth(grant: GrantType, config: PixivConfig, time: OffsetDateTime): AuthResult {
        return oauth(
            grant = grant,
            client = config.client,
            account = config.account,
            refresh = config.refreshToken,
            time = time
        ).also {
            expiresTime = time.withNano(0).plusSeconds(it.expiresIn)
            authInfo = it
            config {
                refreshToken = it.refreshToken
            }
        }
    }
}
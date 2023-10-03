package xyz.cssxsh.pixiv

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.isActive
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import xyz.cssxsh.pixiv.auth.*
import xyz.cssxsh.pixiv.exception.TransferExceptionHandler
import xyz.cssxsh.pixiv.tool.RubyDns
import xyz.cssxsh.pixiv.tool.RubySSLSocketFactory
import xyz.cssxsh.pixiv.tool.RubyX509TrustManager

public abstract class PixivAuthClient : PixivAppClient, Closeable {

    protected open var auth: AuthResult? = null

    protected open var expiresSeconds: Long = 0L

    protected abstract val ignore: suspend (Throwable) -> Boolean

    /**
     * CookiesStorage, 存储Cookie
     */
    public open val storage: CookiesStorage = AcceptAllCookiesStorage()

    protected open val timeout: Long = 30_000L

    protected open fun client(): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json = PixivJson)
        }
        install(HttpTimeout) {
            socketTimeoutMillis = timeout
            connectTimeoutMillis = timeout
            requestTimeoutMillis = null
        }
        install(HttpCookies) {
            storage = this@PixivAuthClient.storage
        }
        ContentEncoding()
        expectSuccess = true
        HttpResponseValidator {
            handleResponseExceptionWithRequest(block = TransferExceptionHandler)
        }
        defaultRequest {
            header(HttpHeaders.CacheControl, "no-cache")
            header(HttpHeaders.Connection, "keep-alive")
            header(HttpHeaders.Pragma, "no-cache")
            config.headers.forEach(::header)
            url("https://www.pixiv.net")
        }
        Auth {
            providers.add(object : AuthProvider {

                @Suppress("OverridingDeprecatedMember")
                @Deprecated("Please use sendWithoutRequest function instead")
                override val sendWithoutRequest: Boolean = false

                override fun sendWithoutRequest(request: HttpRequestBuilder): Boolean {
                    return request.url.host == "app-api.pixiv.net" && request.url.encodedPath.startsWith("/web").not()
                }

                override suspend fun addRequestHeaders(request: HttpRequestBuilder, authHeader: HttpAuthHeader?) {
                    val info = mutex.withLock {
                        auth?.takeIf { expiresSeconds > Clock.System.now().epochSeconds } ?: useHttpClient { client ->
                            val start = Clock.System.now().epochSeconds
                            client.refresh(refreshToken).save(start = start)
                        }
                    }

                    request.headers {
                        val tokenValue = "Bearer ${info.accessToken}"
                        if (contains(HttpHeaders.Authorization)) {
                            remove(HttpHeaders.Authorization)
                        }
                        append(HttpHeaders.Authorization, tokenValue)
                    }
                }

                override fun isApplicable(auth: HttpAuthHeader): Boolean {
                    if (auth.authScheme != AuthScheme.Bearer) return false
                    if (auth !is HttpAuthHeader.Parameterized) return false

                    return auth.parameter("realm") == null
                }
            })
        }
        engine {
            config {
                with(config) {
                    if (proxy.isNotBlank()) {
                        proxy(Url(proxy).toProxy())
                    } else if (config.sni) {
                        sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                        hostnameVerifier { _, _ -> true }
                    }
                    dns(RubyDns(dns, host))
                }
                // StreamResetException: stream was reset: REFUSED_STREAM
                // protocols(listOf(Protocol.HTTP_1_1))
            }
        }
    }

    protected open val clients: MutableList<HttpClient> by lazy { MutableList(3) { client() } }

    protected open var index: Int = 0

    override fun close(): Unit = clients.forEach { it.close() }

    override suspend fun <R> useHttpClient(block: suspend (HttpClient) -> R): R = supervisorScope {
        while (isActive) {
            try {
                return@supervisorScope block(clients[index])
            } catch (cause: Throwable) {
                if (isActive && ignore(cause)) {
                    index = (index + 1) % clients.size
                } else {
                    throw cause
                }
            }
        }
        throw CancellationException()
    }

    protected open val mutex: Mutex = Mutex()

    /**
     * RefreshToken, 用于刷新状态
     * @see auth
     * @see refresh
     */
    override val refreshToken: String
        get() = requireNotNull(auth?.refreshToken ?: config.refreshToken) { "Not Found RefreshToken" }

    /**
     * DeviceToken
     * @see auth
     */
    override val deviceToken: String
        get() = requireNotNull(auth?.deviceToken) { "Not Found DeviceToken" }

    /**
     * 年龄限制
     * @see auth
     */
    override val ageLimit: AgeLimit
        get() = auth?.user?.age ?: AgeLimit.ALL

    /**
     * 认证信息
     */
    override suspend fun info(): AuthResult = mutex.withLock {
        val start = Clock.System.now().epochSeconds
        auth?.takeIf { expiresSeconds > Clock.System.now().epochSeconds } ?: useHttpClient { client ->
            client.refresh(token = refreshToken).save(start = start)
        }
    }

    /**
     * 登录通过验证页面
     * @param block 从验证页面获得 code
     * @see sina
     * @see cookie
     * @see authorize
     */
    override suspend fun login(block: suspend (redirect: Url) -> String): AuthResult = mutex.withLock {
        val start = Clock.System.now().epochSeconds
        val (verifier, parameters) = verifier(time = start)
        val code = block(URLBuilder(REDIRECT_LOGIN_URL).apply { this.parameters.appendAll(parameters) }.build())
        useHttpClient { client ->
            client.authorize(code = code, verifier = verifier).save(start = start)
        }
    }

    /**
     * 刷新状态
     * @see refreshToken
     */
    override suspend fun refresh(): AuthResult = mutex.withLock {
        val start = Clock.System.now().epochSeconds
        useHttpClient { client ->
            client.refresh(token = refreshToken).save(start = start)
        }
    }

    protected open suspend fun AuthResult.save(start: Long): AuthResult = also { result ->
        expiresSeconds = start + result.expiresIn
        auth = result.copy(deviceToken = storage.get(Url(REDIRECT_LOGIN_URL))["device_token"]?.value)
        config {
            refreshToken = result.refreshToken
        }
    }
}
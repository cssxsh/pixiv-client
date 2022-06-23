package xyz.cssxsh.pixiv

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*
import xyz.cssxsh.pixiv.auth.*
import xyz.cssxsh.pixiv.exception.*
import xyz.cssxsh.pixiv.tool.*
import java.time.*

public abstract class PixivAuthClient : PixivAppClient, Closeable {

    protected open var auth: AuthResult? = null

    protected open var expires: OffsetDateTime = OffsetDateTime.MIN

    protected abstract val ignore: suspend (Throwable) -> Boolean

    public open val storage: AcceptAllCookiesStorage = AcceptAllCookiesStorage()

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
            bearer {
                sendWithoutRequest { request ->
                    request.url.host == "app-api.pixiv.net" &&  request.url.encodedPath.startsWith("/web").not()
                }
                loadTokens {
                    mutex.withLock {
                        auth?.takeIf { expires > OffsetDateTime.now() }?.toBearerTokens() ?: useHttpClient { client ->
                            val start = OffsetDateTime.now()
                            client.refresh(refreshToken).save(start = start).toBearerTokens()
                        }
                    }
                }
                refreshTokens {
                    mutex.withLock {
                        val start = OffsetDateTime.now()
                        client.refresh(refreshToken).save(start = start).toBearerTokens()
                    }
                }
            }
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
            } catch (e: Throwable) {
                if (isActive && ignore(e)) {
                    index = (index + 1) % clients.size
                } else {
                    throw e
                }
            }
        }
        throw CancellationException()
    }

    protected open val mutex: Mutex = Mutex()

    override val refreshToken: String get() = requireNotNull(auth?.refreshToken ?: config.refreshToken) { "Not Found RefreshToken" }

    override val ageLimit: AgeLimit get() = auth?.user?.age ?: AgeLimit.ALL

    override suspend fun info(): AuthResult = mutex.withLock {
        val start = OffsetDateTime.now()
        auth?.takeIf { expires > OffsetDateTime.now() } ?: useHttpClient { client ->
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
        val start = OffsetDateTime.now()
        val (verifier, url) = verifier(time = start)
        val code = block(url)
        useHttpClient { client ->
            client.authorize(code = code, verifier = verifier).save(start = start)
        }
    }

    override suspend fun refresh(): AuthResult = mutex.withLock {
        val start = OffsetDateTime.now()
        useHttpClient { client ->
            client.refresh(token = refreshToken).save(start = start)
        }
    }

    protected open suspend fun AuthResult.save(start: OffsetDateTime): AuthResult = also { result ->
        expires = start.withNano(0).plusSeconds(result.expiresIn)
        auth = result
        config {
            refreshToken = result.refreshToken
        }
    }
}
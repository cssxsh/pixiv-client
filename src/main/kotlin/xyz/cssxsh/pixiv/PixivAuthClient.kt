package xyz.cssxsh.pixiv

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.util.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*
import xyz.cssxsh.pixiv.auth.*
import xyz.cssxsh.pixiv.exception.*
import xyz.cssxsh.pixiv.tool.*
import java.time.*

abstract class PixivAuthClient : PixivAppClient, Closeable {

    protected open var authInfo: AuthResult? = null

    protected open var expires: OffsetDateTime = OffsetDateTime.MIN

    protected abstract val ignore: suspend (Throwable) -> Boolean

    open val storage = AcceptAllCookiesStorage()

    protected open val timeout = 30_000L

    protected open fun client() = HttpClient(OkHttp) {
        Json {
            serializer = KotlinxSerializer(PixivJson)
        }
        install(HttpTimeout) {
            socketTimeoutMillis = timeout
            connectTimeoutMillis = timeout
            requestTimeoutMillis = null
        }
        install(HttpCookies) {
            storage = this@PixivAuthClient.storage
        }
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        HttpResponseValidator {
            handleResponseException(block = TransferExceptionHandler)
        }
        defaultRequest {
            header(HttpHeaders.CacheControl, "no-cache")
            header(HttpHeaders.Connection, "keep-alive")
            header(HttpHeaders.Pragma, "no-cache")
            config.headers.forEach(::header)
        }
        Auth {
            providers.add(object : AuthProvider {
                override fun sendWithoutRequest(request: HttpRequestBuilder): Boolean {
                    return request.url.host in listOf("app-api.pixiv.net", "public-api.secure.pixiv.net") &&
                        request.url.encodedPath.startsWith("/web").not()
                }

                @Deprecated("Please use sendWithoutRequest function instead")
                override val sendWithoutRequest: Boolean = true

                override suspend fun addRequestHeaders(request: HttpRequestBuilder) {
                    request.header(HttpHeaders.Authorization, "Bearer ${info().accessToken}")
                }

                override fun isApplicable(auth: HttpAuthHeader): Boolean = false

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

    protected open val clients by lazy { MutableList(3) { client() } }

    protected open var index = 0

    override fun close() = clients.forEach { it.close() }

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

    protected open val mutex = Mutex()

    private val refreshToken get() = requireNotNull(config.refreshToken) { "Not Found RefreshToken" }

    override suspend fun info(): AuthResult = mutex.withLock {
        val start = OffsetDateTime.now()
        authInfo?.takeIf { expires > start } ?: refresh(token = refreshToken).save(start = start)
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
        authorize(code = code, verifier = verifier).save(start = start)
    }

    override suspend fun refresh(): AuthResult = mutex.withLock {
        val start = OffsetDateTime.now()
        refresh(token = refreshToken).save(start = start)
    }

    protected open suspend fun AuthResult.save(start: OffsetDateTime): AuthResult = also {
        expires = start.withNano(0).plusSeconds(it.expiresIn)
        authInfo = it
        config {
            refreshToken = it.refreshToken
        }
    }
}
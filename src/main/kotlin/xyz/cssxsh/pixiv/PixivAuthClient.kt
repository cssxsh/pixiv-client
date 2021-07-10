package xyz.cssxsh.pixiv

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.cssxsh.pixiv.auth.*
import xyz.cssxsh.pixiv.exception.*
import xyz.cssxsh.pixiv.tool.*
import java.time.OffsetDateTime

abstract class PixivAuthClient : PixivAppClient, Closeable {

    protected open var authInfo: AuthResult? = null

    protected open var expires: OffsetDateTime = OffsetDateTime.now().withNano(0)

    protected abstract val ignore: suspend (Throwable) -> Boolean

    open val storage = AcceptAllCookiesStorage()

    protected open val timeout = 30_000L

    protected open val client = HttpClient(OkHttp) {
        Json {
            serializer = KotlinxSerializer(PixivJson)
        }
        install(HttpTimeout) {
            socketTimeoutMillis = timeout
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
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
            config.headers.forEach(::header)
        }

        Auth {
            bearer {
                sendWithoutRequest {
                    it.url.host in listOf("app-api.pixiv.net", "public-api.secure.pixiv.net") &&
                        it.url.encodedPath.startsWith("/web").not()
                }

                loadTokens {
                    info().toBearerTokens()
                }

                refreshTokens {
                    refresh(requireNotNull(config.refreshToken) { "Not Found RefreshToken" }).toBearerTokens()
                }
            }
        }

        engine {
            config {
                config.run {
                    if (config.sni) {
                        sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                        hostnameVerifier { _, _ -> true }
                    }
                    dns(RubyDns(dns, host))
                    proxy(proxy.takeIf { it.isNotBlank() }?.let(::Url)?.toProxy())
                }
            }
        }
    }

    override fun close() = client.close()

    override suspend fun <R> useHttpClient(block: suspend (HttpClient) -> R): R = supervisorScope {
        while (isActive) {
            runCatching {
                block(client)
            }.onSuccess {
                return@supervisorScope  it
            }.onFailure {
                if (isActive && ignore(it)) {
                    // e.printStackTrace()
                } else {
                    throw it
                }
            }
        }
        throw CancellationException()
    }

    protected open val mutex = Mutex()

    override suspend fun info(): AuthResult = mutex.withLock {
        val start = OffsetDateTime.now()
        val token = { requireNotNull(config.refreshToken) { "Not Found RefreshToken" } }
        authInfo?.takeIf { expires > start } ?: (this as UseHttpClient).refresh(token = token()).save(start = start)
    }

    override suspend fun login(block: suspend (Url) -> String): AuthResult = mutex.withLock {
        val start = OffsetDateTime.now()
        val (verifier, url) = verifier(time = start)
        val code = block(url)
        authorize(code = code, verifier = verifier).save(start)
    }

    override suspend fun refresh(token: String): AuthResult = mutex.withLock {
        val start = OffsetDateTime.now()
        (this as UseHttpClient).refresh(token = token).save(start = start)
    }

    protected open suspend fun AuthResult.save(start: OffsetDateTime): AuthResult = also {
        expires = start.withNano(0).plusSeconds(it.expiresIn)
        authInfo = it
        config {
            refreshToken = it.refreshToken
        }
    }
}
package xyz.cssxsh.pixiv

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.cssxsh.pixiv.auth.*
import xyz.cssxsh.pixiv.exception.*
import xyz.cssxsh.pixiv.tool.*
import java.time.OffsetDateTime

abstract class PixivAuthClient : PixivAppClient {

    protected open var authInfo: AuthResult? = null

    protected open var expires: OffsetDateTime = OffsetDateTime.now().withNano(0)

    protected abstract val ignore: suspend (Throwable) -> Boolean

    open val cookiesStorage = AcceptAllCookiesStorage()

    private fun LocalDns(): LocalDns = LocalDns(
        dns = config.dns,
        initHost = config.host,
        cname = config.cname
    )

    protected open fun client(): HttpClient = HttpClient(OkHttp) {
        Json {
            serializer = KotlinxSerializer(PixivJson)
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            requestTimeoutMillis = 30_000
        }
        install(HttpCookies) {
            storage = cookiesStorage
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

        install(PixivAccessToken) {
            taken = {
                info().accessToken
            }
        }

        engine {
            config {
                config.proxy.takeIf { it.isNotBlank() }?.let {
                    proxySelector(ProxySelector(proxy = it, cname = config.cname))
                }

                if (config.useRubySSLFactory) {
                    sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                    hostnameVerifier { _, _ -> true }
                }

                dns(LocalDns())
            }
        }
    }

    override suspend fun <R> useHttpClient(block: suspend (HttpClient) -> R): R = supervisorScope {
        while (isActive) {
            try {
                return@supervisorScope client().use { block(it) }
            } catch (e: Throwable) {
                if (ignore(e)) {
                    // e.printStackTrace()
                } else {
                    throw e
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
        val client = "pixiv-${config.headers["App-OS"]}"
        val (verifier, url) = verifier(client = client, time = start)
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
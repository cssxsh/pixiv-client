package xyz.cssxsh.pixiv

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import xyz.cssxsh.pixiv.auth.*
import xyz.cssxsh.pixiv.exception.*
import xyz.cssxsh.pixiv.tool.*
import java.time.OffsetDateTime

abstract class AuthPixivClient : PixivAppClient {

    protected open var authInfo: AuthResult? = null

    protected open var expiresTime: OffsetDateTime = OffsetDateTime.now().withNano(0)

    protected abstract val apiIgnore: suspend (Throwable) -> Boolean

    private val cookiesStorage = AcceptAllCookiesStorage()

    private fun LocalDns(): LocalDns = LocalDns(
        dns = config.dns,
        initHost = config.host,
        cname = config.cname
    )

    protected open fun client(): HttpClient = HttpClient(OkHttp) {
        Json {
            serializer = KotlinxSerializer()
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 5_000
            connectTimeoutMillis = 5_000
            requestTimeoutMillis = 5_000
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
                getAuthInfo().accessToken
            }
        }

        engine {
            config {
                config.proxy?.let {
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
                if (apiIgnore(e)) {
                    // e.printStackTrace()
                } else {
                    throw e
                }
            }
        }
        throw CancellationException()
    }

    protected open val mutex = Mutex()
}
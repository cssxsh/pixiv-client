package xyz.cssxsh.pixiv.client

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.statement.*
import kotlinx.coroutines.sync.Mutex
import xyz.cssxsh.pixiv.client.exception.AppApiException
import xyz.cssxsh.pixiv.client.exception.AuthException
import xyz.cssxsh.pixiv.client.exception.OtherClientException
import xyz.cssxsh.pixiv.client.exception.PublicApiException
import xyz.cssxsh.pixiv.data.AuthResult
import xyz.cssxsh.pixiv.toProxy
import xyz.cssxsh.pixiv.tool.LocalDns
import xyz.cssxsh.pixiv.tool.RubySSLSocketFactory
import xyz.cssxsh.pixiv.tool.RubyX509TrustManager
import java.io.IOException
import java.net.Proxy
import java.net.ProxySelector
import java.net.SocketAddress
import java.net.URI
import java.time.OffsetDateTime

abstract class AbstractPixivClient : PixivClient {

    protected open var authInfo: AuthResult? = null

    protected open var expiresTime: OffsetDateTime = OffsetDateTime.now().withNano(0)

    protected abstract val apiIgnore: suspend (Throwable) -> Boolean

    private val cookiesStorage = AcceptAllCookiesStorage()

    private fun getLocalDns(): LocalDns = LocalDns(
        dns = config.dns,
        initHost = config.host,
        cname = config.cname
    )

    protected open fun httpClient(): HttpClient = HttpClient(OkHttp) {
        Json {
            serializer = KotlinxSerializer()
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 15_000
            connectTimeoutMillis = 15_000
            requestTimeoutMillis = 15_000
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
            handleResponseException { cause ->
                if (cause is ClientRequestException) {
                    cause.response.readText().let { content ->
                        runCatching {
                            AppApiException(cause.response, content)
                        }.onSuccess {
                            throw it
                        }

                        runCatching {
                            PublicApiException(cause.response, content)
                        }.onSuccess {
                            throw it
                        }

                        runCatching {
                            AuthException(cause.response, content)
                        }.onSuccess {
                            throw it
                        }

                        runCatching {
                            OtherClientException(cause.response, content)
                        }.onSuccess {
                            throw it
                        }
                    }
                }
            }
        }

        install(PixivAccessToken) {
            taken = {
                getAuthInfo().accessToken
            }
        }

        engine {
            config {
                config.proxy?.toProxy()?.let { proxy ->
                    proxySelector(object : ProxySelector() {
                        override fun select(uri: URI?): MutableList<Proxy> = mutableListOf<Proxy>().apply {
                            if (uri?.host !in config.cname) add(proxy)
                        }

                        override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {
                            // println("connectFailed； $uri")
                        }
                    })
                }

                if (config.useRubySSLFactory) {
                    sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                    hostnameVerifier { _, _ -> true }
                }

                dns(getLocalDns())
            }
        }
    }

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

    protected open val mutex = Mutex()

    override suspend fun <R> useHttpClient(block: suspend PixivClient.(HttpClient) -> R): R =
        useHttpClient(apiIgnore, block)
}
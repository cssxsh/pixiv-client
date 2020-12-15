package xyz.cssxsh.pixiv.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okio.ByteString.Companion.toByteString
import xyz.cssxsh.pixiv.client.exception.ApiException
import xyz.cssxsh.pixiv.client.exception.AuthException
import xyz.cssxsh.pixiv.client.exception.OtherClientException
import xyz.cssxsh.pixiv.data.AuthResult
import xyz.cssxsh.pixiv.tool.LocalDns
import java.io.IOException
import java.net.*
import java.time.OffsetDateTime
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class SimplePixivClient(
    parentCoroutineContext: CoroutineContext = EmptyCoroutineContext,
    coroutineName: String = "SimplePixivClient",
    override val config: PixivConfig
) : PixivClient, AbstractPixivClient() {

    constructor(
        parentCoroutineContext: CoroutineContext = EmptyCoroutineContext,
        coroutineName: String = "SimplePixivClient",
        block: PixivConfig.() -> Unit
    ) : this(parentCoroutineContext, coroutineName, PixivConfig().apply(block))

    override val coroutineContext: CoroutineContext by lazy {
        parentCoroutineContext + CoroutineName(coroutineName)
    }

    private fun autoAuthBlock() = runBlocking { autoAuth() }

    override suspend fun getAuthInfo(): AuthResult.AuthInfo = synchronized(expiresTime) {
        if (expiresTime <= OffsetDateTime.now()) authInfo = null
        authInfo ?: autoAuthBlock()
    }

    private val cookiesStorage = AcceptAllCookiesStorage()

    private val host: MutableMap<String, List<InetAddress>> = mutableMapOf()

    override fun httpClient(): HttpClient = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(HttpTimeout) {
            // TODO: Set by Config
            socketTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            requestTimeoutMillis = 60_000
        }
        install(HttpCookies) {
            storage = cookiesStorage
        }
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        expectSuccess = false
        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value
                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response)
                    in 400..499 -> response.run {
                            val content = content.toByteArray().toByteString().string(charset() ?: Charsets.UTF_8)

                            runCatching {
                                ApiException(response, content)
                            }.onSuccess {
                                throw it
                            }

                            runCatching {
                                AuthException(response, content)
                            }.onSuccess {
                                throw it
                            }

                            runCatching {
                                OtherClientException(response, content)
                            }.onSuccess {
                                throw it
                            }

                            throw ClientRequestException(response)
                    }
                    in 500..599 -> throw ServerResponseException(response)
                }

                if (statusCode >= 600) {
                    throw ResponseException(response)
                }
            }
        }
        engine {
            config {
                addInterceptor { chain ->
                    chain.request().newBuilder().apply {
                        config.headers.forEach(::header)
                        if ("X-Client-Hash" !in build().headers.names()) {
                            runBlocking {
                                runCatching {
                                    header("Authorization", "Bearer ${getAuthInfo().accessToken}")
                                }
                            }
                        }
                    }.build().let {
                        chain.proceed(it)
                    }
                }

                Tool.getProxyByUrl(config.proxy)?.let { proxy ->
                    proxySelector(object : ProxySelector() {
                        override fun select(uri: URI?): MutableList<Proxy> = mutableListOf<Proxy>().apply {
                            if ( uri?.host !in config.cname) add(proxy)
                        }
                        override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {
                            println("connectFailed； $uri")
                        }
                    })
                }

                if (config.RubySSLFactory) {
                    sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                    hostnameVerifier { _, _ -> true }
                }

                dns(LocalDns(
                    dnsUrl = config.dns.toHttpUrlOrNull(),
                    host = host,
                    cname = config.cname
                ))
            }
        }
    }
}
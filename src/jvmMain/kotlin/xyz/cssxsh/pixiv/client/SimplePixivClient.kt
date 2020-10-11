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
import okhttp3.Dns
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okio.ByteString.Companion.toByteString
import okhttp3.dnsoverhttps.DnsOverHttps
import xyz.cssxsh.pixiv.client.exception.ApiException
import xyz.cssxsh.pixiv.client.exception.AuthException
import xyz.cssxsh.pixiv.client.exception.OtherClientException
import java.io.IOException
import java.net.*
import kotlin.coroutines.CoroutineContext

actual open class SimplePixivClient
actual constructor(
    parentCoroutineContext: CoroutineContext,
    override val config: PixivConfig
) : PixivClient, AbstractPixivClient() {

    actual constructor(
        parentCoroutineContext: CoroutineContext,
        block: PixivConfig.() -> Unit
    ) : this(parentCoroutineContext, PixivConfig().apply(block))

    override val coroutineContext: CoroutineContext by lazy {
        parentCoroutineContext + CoroutineName("PixivHelper")
    }

    private fun newDns(url: HttpUrl) : Dns = object : Dns {
        private val client = OkHttpClient()
        val doh = DnsOverHttps.Builder().apply {
            client(client)
            url(url)
            includeIPv6(false)
            post(true)
            resolvePrivateAddresses(true)
            resolvePublicAddresses(true)
        }.build()

        val host: MutableMap<String, List<InetAddress>> = mutableMapOf()

        override fun lookup(hostname: String): List<InetAddress> = host.getOrPut(hostname) {
            if (hostIsIp(hostname)) {
                InetAddress.getAllByName(hostname).toList()
            } else {
                config.cname[hostname]?.let { doh.lookup(it) } ?: doh.lookup(hostname)
            }
        }.let {
            if (it.isEmpty()) {
                InetAddress.getAllByName(hostname).toMutableList()
            } else {
                it.toMutableList()
            }
        }.apply {
            shuffle()
            // println("dns: $hostname: $this")
        }
    }

    override fun httpClient(): HttpClient = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        install(HttpTimeout) {
            socketTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
            requestTimeoutMillis = 60_000
        }
        install(HttpCookies) {
            storage = AcceptAllCookiesStorage()
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
                    chain.request().let { request ->
                        request.newBuilder().apply {
                            // headers
                             config.headers.forEach(::header)
                            if (request.url.host !in config.auth.url) {
                                header("Authorization", "Bearer ${getAuthInfoOrThrow().accessToken}")
                            }
                        }.build()
                    }.let {
                        chain.proceed(it)
                    }
                }

//                proxy(Tool.getProxyByUrl(config.proxy))
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
                // dns
                config.dns.toHttpUrlOrNull()?.let { dnsUrl ->
                    dns(newDns(dnsUrl))
                }
            }
        }
    }
}
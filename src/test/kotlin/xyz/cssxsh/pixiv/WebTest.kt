package xyz.cssxsh.pixiv

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.http.*
import org.junit.jupiter.api.Test
import xyz.cssxsh.pixiv.exception.*
import xyz.cssxsh.pixiv.tool.*
import javax.net.ssl.SSLSocket

abstract class WebTest {

    protected val storage = AcceptAllCookiesStorage()


    protected val client by lazy {
        object : PixivWebClient {

            override val storage get() = this@WebTest.storage

            private fun client(): HttpClient = HttpClient(OkHttp) {
                Json {
                    serializer = KotlinxSerializer()
                }
                install(HttpTimeout) {
                    socketTimeoutMillis = 15_000
                    connectTimeoutMillis = 15_000
                    requestTimeoutMillis = null
                }
                install(HttpCookies) {
                    storage = this@WebTest.storage
                }
                ContentEncoding {
                    gzip()
                    deflate()
                    identity()
                }
                HttpResponseValidator {
                    handleResponseException(block = TransferExceptionHandler)
                }

                engine {
                    config {
                        sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                        hostnameVerifier { _, _ -> true }
                    }
                }
            }

            override suspend fun <R> useHttpClient(block: suspend (HttpClient) -> R): R = client().use { block(it) }

            override val config: PixivConfig = PixivConfig()

            override fun config(block: PixivConfig.() -> Unit): PixivConfig = config.apply(block)
        }
    }

    @Test
    fun sos() {
        (RubySSLSocketFactory.createSocket("d1ctzrip8l97jt.cloudfront.net", URLProtocol.HTTPS.defaultPort) as SSLSocket).use {
            it.startHandshake()
            println(it.session.cipherSuite)
        }
    }
}
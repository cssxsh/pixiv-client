package xyz.cssxsh.pixiv

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.core.*
import xyz.cssxsh.pixiv.exception.TransferExceptionHandler
import kotlin.use

abstract class WebTest {

    protected val storage = AcceptAllCookiesStorage()


    protected val client by lazy {
        object : PixivWebClient {

            override val storage get() = this@WebTest.storage

            private fun client(): HttpClient = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(json = PixivJson)
                }
                install(HttpTimeout) {
                    socketTimeoutMillis = 15_000
                    connectTimeoutMillis = 15_000
                    requestTimeoutMillis = null
                }
                install(HttpCookies) {
                    storage = this@WebTest.storage
                }
                ContentEncoding()
                HttpResponseValidator {
                    handleResponseExceptionWithRequest(block = TransferExceptionHandler)
                }

                // TODO set up SSL
            }

            override suspend fun <R> useHttpClient(block: suspend (HttpClient) -> R): R = client().use { block(it) }

            override val config: PixivConfig = PixivConfig()

            override fun config(block: PixivConfig.() -> Unit): PixivConfig = config.apply(block)
        }
    }

    // TODO fix below test
//    @Test
//    fun sos() {
//        (RubySSLSocketFactory.createSocket("d1ctzrip8l97jt.cloudfront.net", URLProtocol.HTTPS.defaultPort) as SSLSocket).use {
//            it.startHandshake()
//            println(it.session.cipherSuite)
//        }
//    }
}
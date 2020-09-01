package xyz.cssxsh.pixiv.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.header
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import okio.ByteString.Companion.toByteString
import xyz.cssxsh.pixiv.client.exception.ApiException
import xyz.cssxsh.pixiv.client.exception.AuthException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

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

    override var httpClient: HttpClient = HttpClient(OkHttp) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        defaultRequest {
            config.headers.forEach(::header)
        }
        expectSuccess = false
        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value
                when (statusCode) {
                    in 300..399 -> throw RedirectResponseException(response)
                    in 400..499 -> {
                        println(response.request.headers)
                        // 判断是否为登录状态
                        when {
                            "grant_type" in response.request.headers -> {
                                response.content.read {
                                    throw AuthException(response, it.toByteString().string(response.charset() ?: Charsets.UTF_8))
                                }
                            }
                            "Authorization" in response.request.headers -> {
                                response.content.read {
                                    throw ApiException(response, it.toByteString().string(response.charset() ?: Charsets.UTF_8))
                                }
                            }
                            else -> throw ClientRequestException(response)
                        }
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
                proxy(Tool.getProxyByUrl(config.proxy))
            }
        }
    }
}
package xyz.cssxsh.pixiv.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.*
import io.ktor.client.features.compression.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.*
import io.ktor.utils.io.*
import io.ktor.utils.io.bits.*
import kotlinx.coroutines.CoroutineName
import xyz.cssxsh.pixiv.client.exception.ApiException
import xyz.cssxsh.pixiv.client.exception.AuthException
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

    @KtorExperimentalAPI
    override var httpClient: HttpClient = HttpClient(Js) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
        ContentEncoding {
            gzip()
            deflate()
            identity()
        }
        defaultRequest {
            config.headers.forEach { header(it.key, it.value) }
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
                                response.content.read { source, start, endExclusive ->
                                    throw AuthException(response, ByteArray(source.size32).also {
                                        source.copyTo(it, start.toInt(), endExclusive.toInt())
                                    }.decodeToString())
                                }
                            }
                            "Authorization" in response.request.headers -> {
                                response.content.read { source, start, endExclusive ->
                                    throw ApiException(response, ByteArray(source.size32).also {
                                        source.copyTo(it, start.toInt(), endExclusive.toInt())
                                    }.decodeToString())
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
            proxy = Tool.getProxyByUrl(config.proxy)
        }
    }
}
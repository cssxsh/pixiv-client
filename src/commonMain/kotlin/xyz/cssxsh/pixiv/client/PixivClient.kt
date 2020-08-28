package xyz.cssxsh.pixiv.client

import io.ktor.client.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import xyz.cssxsh.pixiv.HeadersMap
import xyz.cssxsh.pixiv.Method
import xyz.cssxsh.pixiv.ParamsMap
import xyz.cssxsh.pixiv.client.exception.NotLoginException

/**
 * PixivClient，Multiplatform interface
 */
interface PixivClient {

    val ktorClient : HttpClient

    var defaultHeadersMap: HeadersMap

    /**
     * @see <a href="https://developer.mozilla.org/docs/Web/HTTP/Headers/Accept-Language">Accept-Language<a>
     */
    var language: String

    val refreshToken: String

    /**
     * For a example http://127.0.0.1:1080
     */
    var proxy: String?

    val islogined: Boolean

    fun checkLogin() {
        if (islogined.not()) throw NotLoginException()
    }

    suspend fun login(mailOrPixivID: String, password: String)

    suspend fun refresh(refreshToken: String)

    suspend fun callMethod(
        apiUrl: String,
        method: Method,
        paramsMap: ParamsMap = mapOf(),
        headersMap: HeadersMap = defaultHeadersMap
    ): String

    suspend fun <T> useRESTful(
        apiUrl: String,
        method: Method,
        deserializer:  DeserializationStrategy<T>,
        paramsMap: ParamsMap = mapOf(),
        headersMap: HeadersMap = defaultHeadersMap
    ): T = Json.decodeFromString(deserializer, callMethod(
        apiUrl = apiUrl,
        method = method,
        paramsMap = paramsMap,
        headersMap = headersMap
    ))

    suspend fun download(
        fileUrl: String,
        headersMap: HeadersMap = defaultHeadersMap
    ): ByteArray
}
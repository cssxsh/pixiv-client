package xyz.cssxsh.pixiv.client

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import xyz.cssxsh.pixiv.client.exception.NotLoginException

/**
 * PixivClient，Multiplatform interface
 */
interface PixivClient {

    val defaultHeadersMap: HeadersMap

    fun isLogined(): Boolean

    fun checkLogin() {
        if (isLogined().not()) throw NotLoginException()
    }

    suspend fun login(mailOrPixivID: String, password: String)

    suspend fun refresh(refreshToken: String)

    fun getRefreshToken(): String

    fun setProxy(proxyUrl: String)

    fun setLanguage(language: String)

    suspend fun httpGet(apiUrl: String, paramsMap: ParamsMap, headersMap: HeadersMap = defaultHeadersMap): String

    suspend fun <T> httpGet(deserialize: DeserializationStrategy<T>, apiUrl: String, paramsMap: ParamsMap, headersMap: HeadersMap = defaultHeadersMap): T

    suspend fun httpPost(apiUrl: String, paramsMap: ParamsMap, headersMap: HeadersMap = defaultHeadersMap): String

    suspend fun <T> httpPost(deserialize: DeserializationStrategy<T>, apiUrl: String, paramsMap: ParamsMap, headersMap: HeadersMap = defaultHeadersMap): T

    suspend fun httpDelete(apiUrl: String, paramsMap: ParamsMap, headersMap: HeadersMap = defaultHeadersMap): String

    suspend fun <T> httpDelete(deserialize: DeserializationStrategy<T>, apiUrl: String, paramsMap: ParamsMap, headersMap: HeadersMap = defaultHeadersMap): T
}
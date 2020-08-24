package xyz.cssxsh.pixiv.client

import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URI
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okio.IOException
import xyz.cssxsh.pixiv.client.data.AuthResult
import xyz.cssxsh.pixiv.client.exception.*

actual class SimplePixivClient(
    proxy: Proxy = Proxy.NO_PROXY,
    defaultHeadersMap: HeadersMap,
    clientId: String,
    clientSecret: String,
    hashSecret: String,
    acceptLanguage: String
) : PixivClient, AbstractPixivClient(
    proxy = proxy,
    defaultHeadersMap = defaultHeadersMap,
    clientId = clientId,
    clientSecret = clientSecret,
    hashSecret = hashSecret,
    acceptLanguage = acceptLanguage
) {
    /**
     * Simple pixiv client implementation
     * @param proxyUrl For example http://127.0.0.1:1080
     * @param acceptLanguage HTTP Header Accept-Language
     * @see <a href="https://developer.mozilla.org/docs/Web/HTTP/Headers/Accept-Language">Accept-Language<a>
     * @author cssxsh
     */
    actual constructor(
        proxyUrl: String?,
        acceptLanguage: String
    ) : this(
        proxy = getProxyByUrl(proxyUrl),
        defaultHeadersMap = Util.DEFAULT_HEADERS_MAP,
        clientId = Util.CLIENT_ID,
        clientSecret = Util.CLIENT_SECRET,
        hashSecret = Util.HASH_SECRET,
        acceptLanguage = acceptLanguage
    )

    companion object {
        private fun getProxyByUrl(proxyUrl: String?): Proxy {
            fun URI.getPort(defaultPort: Int): Int =
                if (this.port == -1) defaultPort else this.port

            try {
                if (proxyUrl.isNullOrEmpty()) {
                    return Proxy.NO_PROXY
                }
                val proxy = URI(proxyUrl)
                return when (proxy.scheme) {
                    null -> Proxy.NO_PROXY
                    "http" -> Proxy(Proxy.Type.HTTP, InetSocketAddress(proxy.host, proxy.getPort(80)))
                    "socks4" -> Proxy(Proxy.Type.SOCKS, InetSocketAddress(proxy.host, proxy.getPort(1080)))
                    "socks5" -> Proxy(Proxy.Type.SOCKS, InetSocketAddress(proxy.host, proxy.getPort(1080)))
                    else -> throw ProxyException(proxyUrl.toString())
                }
            } catch (e: Exception) {
                throw ProxyException(proxyUrl.toString())
            }
        }
    }

    override fun isLogined(): Boolean = (authInfo != null)

    override suspend fun login(mailOrPixivID: String, password: String) =
        auth(grantType = GrantType.PASSWORD, username = mailOrPixivID, password = password)

    override suspend fun refresh(refreshToken: String) =
        auth(grantType = GrantType.REFRESH, refreshToken = refreshToken)

    override fun setProxy(proxyUrl: String) {
        okHttpClient = okHttpClient.newBuilder()
            .proxy(getProxyByUrl(proxyUrl))
            .build()
    }

    override fun setLanguage(language: String) {
        acceptLanguage = language
    }

    override fun getRefreshToken(): String {
        checkLogin()
        return authInfo!!.refreshToken
    }

    private fun getAuthorization(): String {
        checkLogin()
        return authInfo!!.run {
            when (tokenType) {
                "bearer" -> "Bearer $accessToken"
                else -> "$tokenType $accessToken"
            }
        }
    }

    private fun getHash(text: String): String {
        val instance: MessageDigest = MessageDigest.getInstance("MD5")
        return instance.digest((text + hashSecret).toByteArray(Charsets.UTF_8)).joinToString("") {
            it.toUByte().toString(16)
        }
    }

    @Throws(IOException::class)
    private fun auth(
        grantType: GrantType,
        username: String = "",
        password: String = "",
        refreshToken: String = ""
    ) {
        val localTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").format(Date())
        val hash = getHash(localTime)
        val headers: Headers = Headers.Builder()
            .add("X-Client-Hash", hash)
            .add("X-Client-Time", localTime)
            .build()
        val requestBody: FormBody = FormBody.Builder()
            .add("get_secure_url" , "1")
            .add("client_id" , clientId)
            .add("client_secret" , clientSecret)
            .add("grant_type" , grantType.text)
            .add("username", username)
            .add("password", password)
            .add("refresh_token", refreshToken)
            .build()
        val request: Request = Request.Builder()
            .headers(headers)
            .url(Util.OAUTH_URL)
            .post(requestBody)
            .build()

        val response: Response = okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            val jsonText = response.body!!.string()
            val authResult: AuthResult = Json.decodeFromString(AuthResult.serializer(), jsonText)
            authInfo = authResult.info
        } else {
            throw AuthException(response.body!!.string())
        }
    }

    @Throws(IOException::class)
    private fun httpRequest(
        apiUrl: String,
        method: Method,
        paramsMap: ParamsMap,
        headersMap: HeadersMap
    ): String {
        val headers: Headers = Headers.Builder().run {
            headersMap.forEach {
                add(it.key, it.value)
            }
            add("Authorization", getAuthorization())
            build()
        }
        val httpUrl: HttpUrl = apiUrl.toHttpUrlOrNull()!!.newBuilder().run {
            paramsMap.forEach {
                addQueryParameter(it.key, it.value.toString())
            }
            build()
        }
        val requestBody: FormBody? = if (method == Method.POST) FormBody.Builder().run {
            paramsMap.forEach {
                add(it.key, it.value.toString())
            }
            build()
        } else null
        val request: Request = Request.Builder()
            .headers(headers)
            .url(httpUrl)
            .method(method.text, requestBody)
            .build()
        val response: Response = okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            return response.body!!.string()
        } else {
            throw ApiException(response.body!!.string())
        }
    }

    @Throws(IOException::class)
    override suspend fun httpGet(apiUrl: String, paramsMap: ParamsMap, headersMap: HeadersMap): String =
        httpRequest(apiUrl, Method.GET, paramsMap, headersMap)

    @Throws(IOException::class)
    override suspend fun <T> httpGet(
        deserialize: DeserializationStrategy<T>,
        apiUrl: String,
        paramsMap: ParamsMap,
        headersMap: HeadersMap
    ): T = Json.decodeFromString(deserialize, httpGet(apiUrl, paramsMap, headersMap))

    @Throws(IOException::class)
    override suspend fun httpPost(apiUrl: String, paramsMap: ParamsMap, headersMap: HeadersMap): String =
        httpRequest(apiUrl, Method.POST, paramsMap, headersMap)

    @Throws(IOException::class)
    override suspend fun <T> httpPost(
        deserialize: DeserializationStrategy<T>,
        apiUrl: String,
        paramsMap: ParamsMap,
        headersMap: HeadersMap
    ): T = Json.decodeFromString(deserialize, httpPost(apiUrl, paramsMap, headersMap))

    @Throws(IOException::class)
    override suspend fun httpDelete(apiUrl: String, paramsMap: ParamsMap, headersMap: HeadersMap): String =
        httpRequest(apiUrl, Method.DELETE, paramsMap, headersMap)

    @Throws(IOException::class)
    override suspend fun <T> httpDelete(
        deserialize: DeserializationStrategy<T>,
        apiUrl: String,
        paramsMap: ParamsMap,
        headersMap: HeadersMap
    ): T = Json.decodeFromString(deserialize, httpDelete(apiUrl, paramsMap, headersMap))
}
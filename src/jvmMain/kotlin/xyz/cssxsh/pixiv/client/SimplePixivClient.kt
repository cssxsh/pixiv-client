package xyz.cssxsh.pixiv.client

import com.soywiz.klock.wrapped.WDateTime
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okio.IOException
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.data.AuthResult
import xyz.cssxsh.pixiv.client.exception.AuthException
import xyz.cssxsh.pixiv.client.Tool.getProxyByUrl
import xyz.cssxsh.pixiv.client.Tool.getMd5Hash
import java.io.InputStream

/**
 * Simple pixiv client implementation
 * @author cssxsh
 */
actual open class SimplePixivClient actual constructor(proxy: String?) : PixivClient, AbstractPixivClient() {

    init {
        httpClient = OkHttpClient.Builder().proxy(getProxyByUrl(proxy)).build()
    }

    override val islogined: Boolean
        get() = (authInfo != null)

    override suspend fun login(mailOrPixivID: String, password: String) =
        auth(grantType = GrantType.PASSWORD, username = mailOrPixivID, password = password)

    override suspend fun refresh(refreshToken: String) =
        auth(grantType = GrantType.REFRESH_TOKEN, refreshToken = refreshToken)

    override var proxy
        get() = httpClient.proxy?.toString()
        set(value) {
            httpClient = httpClient.newBuilder()
                .proxy(getProxyByUrl(value))
                .build()
        }

    override val refreshToken: String
        get() {
            checkLogin()
            return authInfo!!.refreshToken
        }

    private val authorization: String
        get() {
            checkLogin()
            return authInfo!!.run {
                when (tokenType) {
                    "bearer" -> "Bearer $accessToken"
                    else -> "$tokenType $accessToken"
                }
            }
        }

    @Throws(IOException::class)
    private suspend fun auth(
        grantType: GrantType,
        username: String = "",
        password: String = "",
        refreshToken: String = ""
    ) = coroutineScope {
        val localTime = WDateTime.now().format("yyyy-MM-dd'T'HH:mm:ssXXX")
        val hash = getMd5Hash(localTime + hashSecret)
        val headers: Headers = Headers.Builder()
            .add("X-Client-Hash", hash)
            .add("X-Client-Time", localTime)
            .add("Accept-Language", language)
            .build()
        val requestBody: FormBody = FormBody.Builder()
            .add("get_secure_url", "1")
            .add("client_id", clientId)
            .add("client_secret", clientSecret)
            .add("grant_type", grantType.value())
            .add("username", username)
            .add("password", password)
            .add("refresh_token", refreshToken)
            .build()
        val request: Request = Request.Builder()
            .headers(headers)
            .url(Util.OAUTH_URL)
            .post(requestBody)
            .build()

        val channel: Channel<AuthResult> = Channel()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                channel.close(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonText = response.body!!.string()
                if (response.isSuccessful) {
                    val authResult: AuthResult = Json.decodeFromString(AuthResult.serializer(), jsonText)
                    channel.sendBlocking(authResult)
                } else {
                    channel.close(AuthException(jsonText))
                }
            }
        })
        authInfo = channel.receive().info
    }

    @Throws(IOException::class)
    fun callRequest(
        apiUrl: String,
        method: Method,
        paramsMap: ParamsMap,
        headersMap: HeadersMap,
    ): ResponseBody {
        val headers: Headers = Headers.Builder().apply {
            defaultHeadersMap.forEach {
                add(it.key, it.value)
            }
            headersMap.forEach {
                add(it.key, it.value)
            }
            add("Authorization", authorization)
            add("Accept-Language", language)
        }.build()
        val params: List<Pair<String, String>> = paramsMap.asIterable().flatMap {
            when (it.value) {
                is ParamEnum -> listOf(it.key to (it.value as ParamEnum).value())
                is Iterable<*> -> (it.value as Iterable<*>).mapIndexed { index, any ->
                    "${it.key}[$index]" to any.toString()
                }
                null -> listOf()
                else -> listOf(it.key to it.value.toString())
            }
        }
        val httpUrl: HttpUrl = apiUrl.toHttpUrl().newBuilder().apply {
            if (method == Method.GET) params.forEach {
                addQueryParameter(it.first, it.second)
            }
        }.build()
        val requestBody: FormBody? = if (method == Method.POST) FormBody.Builder().apply {
            params.forEach {
                add(it.first, it.second)
            }
        }.build() else null
        val request: Request = Request.Builder().apply {
            headers(headers)
            url(httpUrl)
            method(method.name, requestBody)
        }.build()

        return httpClient.newCall(request).execute().body!!
    }

    @Throws(IOException::class)
    override suspend fun callMethod(
        apiUrl: String,
        method: Method,
        paramsMap: ParamsMap,
        headersMap: HeadersMap
    ): String = callRequest(apiUrl, method, paramsMap, headersMap).string()

    // TODO: JVM InputSteam
    @Throws(IOException::class)
    override suspend fun download(
        fileUrl: String,
        headersMap: HeadersMap
    ): ByteArray = downloadToStream(fileUrl = fileUrl, headersMap = headersMap).readBytes()

    // TODO: JVM InputStream
    @Throws(IOException::class)
    suspend fun downloadToStream(
        fileUrl: String,
        headersMap: HeadersMap
    ): InputStream = coroutineScope {
        val headers: Headers = Headers.Builder().apply {
            headersMap.forEach {
                add(it.key, it.value)
            }
            add("Authorization", authorization)
        }.build()
        val httpUrl: HttpUrl = fileUrl.toHttpUrl()
        val request: Request = Request.Builder().apply {
            headers(headers)
            url(httpUrl)
        }.build()
        val channel: Channel<InputStream> = Channel()

        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                channel.close(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val inputStream = response.body!!.byteStream()
                if (response.isSuccessful) {
                    channel.sendBlocking(inputStream)
                } else {
                    // TODO: New Error type
                    channel.close(Exception("download file fail."))
                }
            }
        })

        return@coroutineScope channel.receive()
    }
}
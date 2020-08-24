package xyz.cssxsh.pixiv.client

import okhttp3.OkHttpClient
import xyz.cssxsh.pixiv.client.data.AuthResult.AuthInfo
import java.net.Proxy

abstract class AbstractPixivClient(
    proxy: Proxy,
    override val defaultHeadersMap: HeadersMap,
    protected val clientId: String,
    protected val clientSecret: String,
    protected val hashSecret: String,
    protected var acceptLanguage: String
) : PixivClient {

    var authInfo: AuthInfo? = null

    protected var okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .proxy(proxy)
        .build()


    // abstract fun setSSLContext(sslContext: SSLContext): Unit
}
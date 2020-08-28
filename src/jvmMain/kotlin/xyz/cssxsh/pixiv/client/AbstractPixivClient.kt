package xyz.cssxsh.pixiv.client

import okhttp3.OkHttpClient
import org.intellij.lang.annotations.Language
import xyz.cssxsh.pixiv.HeadersMap
import xyz.cssxsh.pixiv.data.AuthResult.AuthInfo
import java.net.Proxy

abstract class AbstractPixivClient: PixivClient {

    override var defaultHeadersMap: HeadersMap = Util.DEFAULT_HEADERS_MAP

    open var clientId: String = Util.CLIENT_ID

    open var clientSecret: String = Util.CLIENT_SECRET

    open var hashSecret: String = Util.HASH_SECRET

    override var language: String = "jp"

    open var authInfo: AuthInfo? = null

    lateinit var httpClient: OkHttpClient
}
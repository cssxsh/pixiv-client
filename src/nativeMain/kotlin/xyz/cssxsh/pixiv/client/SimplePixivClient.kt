package xyz.cssxsh.pixiv.client

import xyz.cssxsh.pixiv.HeadersMap
import xyz.cssxsh.pixiv.Method
import xyz.cssxsh.pixiv.ParamsMap

/**
 * Simple pixiv client implementation
 * @param proxy For example http://127.0.0.1:1080\
 * @author cssxsh
 */
actual open class SimplePixivClient actual constructor(proxy: String?) : PixivClient {
    override var defaultHeadersMap: HeadersMap
        get() = TODO("Not yet implemented")
        set(value) {}

    override var language: String
        get() = TODO("Not yet implemented")
        set(value) {}

    override val refreshToken: String
        get() = TODO("Not yet implemented")

    override var proxy: String?
        get() = TODO("Not yet implemented")
        set(value) {}

    override val islogined: Boolean
        get() = TODO("Not yet implemented")

    override suspend fun login(mailOrPixivID: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(refreshToken: String) {
        TODO("Not yet implemented")
    }

    override suspend fun callMethod(
        apiUrl: String,
        method: Method,
        paramsMap: ParamsMap,
        headersMap: HeadersMap
    ): String {
        TODO("Not yet implemented")
    }

    override suspend fun download(fileUrl: String, headersMap: HeadersMap): ByteArray {
        TODO("Not yet implemented")
    }
}
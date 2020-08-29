package xyz.cssxsh.pixiv.client

import io.ktor.client.engine.ProxyConfig
import io.ktor.client.engine.ProxyBuilder
import io.ktor.http.Url
import xyz.cssxsh.pixiv.client.exception.ProxyException

object Tool {
    fun getProxyByUrl(proxyUrl: String?): ProxyConfig? = runCatching {
        if (proxyUrl.isNullOrEmpty()) {
            null
        } else {
            val proxy = Url(proxyUrl)
            when (proxy.protocol.name) {
                "http" -> ProxyBuilder.http(proxy)
                "socks" -> ProxyBuilder.socks(proxy.host, proxy.port)
                else -> throw ProxyException(proxyUrl.toString())
            }
        }
    }.getOrNull()
}
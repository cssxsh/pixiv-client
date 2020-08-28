package xyz.cssxsh.pixiv.client

import xyz.cssxsh.pixiv.client.exception.ProxyException
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URI
import java.security.MessageDigest


internal object Tool {

    private val Md5Digest: MessageDigest =  MessageDigest.getInstance("MD5")

    fun getProxyByUrl(proxyUrl: String?): Proxy {
        fun URI.getPort(defaultPort: Int): Int = if (this.port == -1) defaultPort else this.port

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

    fun getMd5Hash(text: String): String =
        Md5Digest.digest(text.toByteArray())!!.contentToString()
}

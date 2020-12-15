package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import okhttp3.Dns
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress

class LocalDns(
    dnsUrl: HttpUrl?,
    private val host: MutableMap<String, List<InetAddress>> = mutableMapOf(),
    private val cname: Map<String, String> = mapOf(),
) : Dns {

    private val dns = dnsUrl?.let { url ->
        DnsOverHttps.Builder().apply {
            client(OkHttpClient())
            includeIPv6(false)
            url(url)
            post(true)
            resolvePrivateAddresses(true)
            resolvePublicAddresses(true)
        }.build()
    } ?: Dns.SYSTEM

    override fun lookup(hostname: String): List<InetAddress> = host.getOrPut(hostname) {
        if (hostIsIp(hostname)) {
            InetAddress.getAllByName(hostname).toList()
        } else {
            cname[hostname]?.let { dns.lookup(it) } ?: dns.lookup(hostname)
        }
    }.let {
        if (it.isEmpty()) {
            InetAddress.getAllByName(hostname).toMutableList()
        } else {
            it.toMutableList()
        }
    }.apply {
        shuffle()
    }
}
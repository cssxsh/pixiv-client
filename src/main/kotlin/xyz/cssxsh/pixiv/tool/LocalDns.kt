package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import okhttp3.Dns
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import java.net.InetAddress

class LocalDns(
    dnsUrl: HttpUrl? = null,
    private val host: MutableMap<String, List<InetAddress>> = mutableMapOf(),
    private val cname: Map<String, String> = mapOf(),
) : Dns {

    constructor (
        dns: String,
        initHost: Map<String, List<String>> = mapOf(),
        cname: Map<String, String> = mapOf(),
    ): this(
        dnsUrl = dns.toHttpUrlOrNull(),
        host = initHost.mapValues { (_, ips) -> ips.map { tran(ipv4 = it) } }.toMutableMap(),
        cname = cname
    )

    companion object {
        private val HttpClient = OkHttpClient()

        private fun tran(ipv4: String): InetAddress =
            InetAddress.getByAddress(ipv4.split('.').map { it.toUByte() }.toUByteArray().toByteArray())
    }

    private val dns: Dns = dnsUrl?.let { url ->
        DnsOverHttps.Builder().apply {
            client(HttpClient)
            includeIPv6(false)
            url(url)
            post(true)
            resolvePrivateAddresses(false)
            resolvePublicAddresses(true)
        }.build()
    } ?: Dns.SYSTEM

    override fun lookup(hostname: String): List<InetAddress> {
        return host.getOrPut(hostname) {
            if (hostIsIp(hostname)) {
                InetAddress.getAllByName(hostname).toList()
            } else {
                cname[hostname]?.let { dns.lookup(it) } ?: dns.lookup(hostname)
            }
        }.toMutableList().apply {
            if (isEmpty()) {
                addAll(InetAddress.getAllByName(hostname))
            }
            shuffle()
        }
    }
}
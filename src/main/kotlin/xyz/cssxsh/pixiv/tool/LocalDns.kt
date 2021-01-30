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
        host = initHost.mapValues { (_, ips) -> ips.map { InetAddress.getByName(it) } }.toMutableMap(),
        cname = cname
    )

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
    }.toMutableList().apply {
        if (isEmpty()) {
            addAll(InetAddress.getAllByName(hostname))
        }
        shuffle()
    }
}
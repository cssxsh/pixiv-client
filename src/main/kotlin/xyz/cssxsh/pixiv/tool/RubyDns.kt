package xyz.cssxsh.pixiv.tool

import okhttp3.Dns
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.dnsoverhttps.DnsOverHttps
import okhttp3.internal.canParseAsIpAddress
import java.net.InetAddress
import java.net.UnknownHostException

class RubyDns(doh: String, private val hosts: Map<String, List<String>>) : Dns {
    private val dns: Dns = (if (doh.isNotBlank()) DnsOverHttps(doh) else Dns.SYSTEM)

    private fun DnsOverHttps(url: String, sni: Boolean = true): DnsOverHttps {
        return DnsOverHttps.Builder().apply {
            client(OkHttpClient.Builder().apply {
                if (sni) {
                    sslSocketFactory(RubySSLSocketFactory, RubyX509TrustManager)
                    hostnameVerifier { _, _ -> true }
                }
            }.build())
            includeIPv6(false)
            url(url.toHttpUrl())
            post(true)
            resolvePrivateAddresses(false)
            resolvePublicAddresses(true)
        }.build()
    }

    override fun lookup(hostname: String): List<InetAddress> {
        val lookup: (String) -> List<InetAddress> = {
            if (hostname.canParseAsIpAddress()) InetAddress.getAllByName(it).asList() else dns.lookup(it)
        }
        val result = mutableListOf<InetAddress>()
        val other = hosts[hostname].orEmpty()

        other.forEach {
            runCatching {
                result.addAll(lookup(it).asReversed())
            }
        }

        runCatching {
            result.addAll(lookup(hostname))
        }

        if (result.isEmpty()) runCatching {
            result.addAll(InetAddress.getAllByName(hostname))
        }

        return result.apply {
            if (isEmpty()) throw UnknownHostException("$hostname and CNAME${other} ")
        }
    }
}
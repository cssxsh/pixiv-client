package xyz.cssxsh.pixiv.tool

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.dnsoverhttps.*
import okhttp3.internal.*
import java.net.*

class RubyDns(doh: String, private val hosts: Map<String, List<String>>) : Dns {
    private val dns: Dns = (if (doh.isNotBlank()) DnsOverHttps(doh) else Dns.SYSTEM)

    private fun DnsOverHttps(url: String): DnsOverHttps {
        return DnsOverHttps.Builder().apply {
            client(OkHttpClient())
            url(url.toHttpUrl())
            post(true)
            resolvePrivateAddresses(false)
            resolvePublicAddresses(true)
        }.build()
    }

    private val lookup: (String) -> List<InetAddress> = {
        if (it.canParseAsIpAddress()) InetAddress.getAllByName(it).asList() else dns.lookup(it)
    }

    override fun lookup(hostname: String): List<InetAddress> {

        val result = mutableListOf<InetAddress>()
        val other = hosts[hostname] ?: hosts.firstNotNullOfOrNull { (host, cname) ->
            cname.takeIf {
                host.startsWith("*.") && hostname.endsWith(host.removePrefix("*."))
            }
        }

        other.orEmpty().forEach {
            runCatching {
                result.addAll(it.let(lookup))
            }
        }

        result.shuffle()

        if (result.isEmpty()) runCatching {
            result.addAll(hostname.let(lookup))
        }

        if (result.isEmpty()) runCatching {
            result.addAll(InetAddress.getAllByName(hostname))
        }

        return result.apply {
            if (isEmpty()) throw UnknownHostException("$hostname and CNAME${other} ")
        }
    }
}
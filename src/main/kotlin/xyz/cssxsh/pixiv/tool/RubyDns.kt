package xyz.cssxsh.pixiv.tool

import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.dnsoverhttps.*
import okhttp3.internal.*
import java.net.*

class RubyDns(doh: String, private val hosts: Map<String, List<String>>) : Dns {
    private val dns: Dns = (if (doh.isNotBlank()) DnsOverHttps(doh) else Dns.SYSTEM)

    private fun DnsOverHttps(url: String): DnsOverHttps {
        return DnsOverHttps.Builder()
            .client(OkHttpClient())
            .url(url.toHttpUrl())
            .post(true)
            .resolvePrivateAddresses(false)
            .resolvePublicAddresses(true)
            .build()
    }

    private val lookup: (String) -> List<InetAddress> = {
        if (it.canParseAsIpAddress()) InetAddress.getAllByName(it).asList() else dns.lookup(it)
    }

    override fun lookup(hostname: String): List<InetAddress> {

        val result = mutableListOf<InetAddress>()
        val other = hosts[hostname] ?: hosts.firstNotNullOfOrNull { (host, cname) ->
            cname.takeIf { hostname.endsWith(host.removePrefix("*.")) }
        }

        for (item in other.orEmpty()) try {
            result.addAll(item.let(lookup))
        } catch (_: Throwable) {
            //
        }

        result.shuffle()

        if (result.isEmpty()) try {
            result.addAll(hostname.let(lookup))
        } catch (_: Throwable) {
            //
        }

        if (result.isEmpty()) try {
            result.addAll(InetAddress.getAllByName(hostname))
        } catch (_: Throwable) {
            //
        }

        return result.apply {
            if (isEmpty()) throw UnknownHostException("$hostname and CNAME${other} ")
        }
    }

    override fun toString(): String {
        return "RubyDns(doh=${(dns as? DnsOverHttps)?.url}, hosts=${hosts})"
    }
}
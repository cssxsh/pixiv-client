package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import okhttp3.Dns
import xyz.cssxsh.pixiv.*
import java.net.InetSocketAddress
import kotlin.coroutines.CoroutineContext

open class RubyProxy(private val dns: Dns = RubyDns(doh = JAPAN_DNS, hosts = DEFAULT_PIXIV_HOST)) : CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    fun listen(host: String = "127.0.0.1", post: Int = 8080) = launch(SupervisorJob()) {
        val server = aSocket(ActorSelectorManager(coroutineContext))
            .tcp()
            .bind(host, post)

        while (isActive) {
            val client = server.accept().connection()

            launch(SupervisorJob()) {
                val request = buildString {
                    while (isActive) {
                        val line = requireNotNull(client.input.readUTF8Line()) { "line is null." }
                        if (line.isEmpty()) break
                        append(line).append("\r\n")
                    }
                }
                val method = request.substringBefore(' ')
                val remote = if (method != "CONNECT") {
                    val url = Url(request.substringAfter(' ').substringBefore(' '))

                    aSocket(ActorSelectorManager(coroutineContext))
                        .tcp()
                        .connect(InetSocketAddress(dns.lookup(hostname = url.host).first(), url.port))
                        .connection()
                        .apply { output.writeStringUtf8(request) }
                } else {
                    val result = """(?<=CONNECT )([\w.-]+):(\d+)""".toRegex().find(request)
                        ?: throw IllegalArgumentException("request error. \n$request")

                    val (hostname, port) = result.destructured

                    aSocket(ActorSelectorManager(coroutineContext))
                        .tcp()
                        .connect(InetSocketAddress(dns.lookup(hostname = hostname).first().hostName, port.toInt()))
                        .connection()
                        .apply { client.output.writeStringUtf8("HTTP/1.1 200 Connection Established\r\n\r\n") }
                }

                println("${client.socket.remoteAddress} $method ${remote.socket.remoteAddress}")

                launch {
                    client.input.copyTo(remote.output)
                }
                launch {
                    remote.input.copyTo(client.output)
                }
            }.invokeOnCompletion { cause ->
                if (cause != null) client.socket.close()
            }
        }
    }
}
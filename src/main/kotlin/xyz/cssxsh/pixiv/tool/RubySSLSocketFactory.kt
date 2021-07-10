package xyz.cssxsh.pixiv.tool

import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.*

object RubySSLSocketFactory : SSLSocketFactory() {

    internal val regexes = mutableListOf("""(pixiv|pximg)\.net""".toRegex())

    private fun Socket.setServerNames(): Socket = apply {
        if (this !is SSLSocket) return@apply

        sslParameters = sslParameters.apply {
            serverNames = serverNames.orEmpty().filter { name ->
                if (name !is SNIHostName) return@filter false
                regexes.none { it in name.asciiName }
            }
        }
    }

    private val socketFactory: SSLSocketFactory = SSLContext.getDefault().socketFactory

    override fun createSocket(socket: Socket?, host: String?, port: Int, autoClose: Boolean): Socket? =
        socketFactory.createSocket(socket, host, port, autoClose)?.setServerNames()

    override fun createSocket(host: String?, port: Int): Socket? =
        socketFactory.createSocket(host, port)?.setServerNames()

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket? =
        socketFactory.createSocket(host, port, localHost, localPort)?.setServerNames()

    override fun createSocket(host: InetAddress?, port: Int): Socket? =
        socketFactory.createSocket(host, port)?.setServerNames()

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket? =
        socketFactory.createSocket(address, port, localAddress, localPort)?.setServerNames()

    override fun getDefaultCipherSuites(): Array<String> = emptyArray()

    override fun getSupportedCipherSuites(): Array<String> = emptyArray()
}
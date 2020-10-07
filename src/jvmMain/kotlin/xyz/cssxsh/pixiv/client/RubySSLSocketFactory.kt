package xyz.cssxsh.pixiv.client

import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

object RubySSLSocketFactory : SSLSocketFactory() {

    private fun Socket.setServerNames(): Socket = when(this) {
        is SSLSocket -> apply {
            println("inetAddress: ${inetAddress}, \"serverNames: ${sslParameters.serverNames}, Protocols: ${sslParameters.protocols.map { it.toString() }}.")
            enabledProtocols = supportedProtocols
            enabledCipherSuites = supportedCipherSuites
            sslParameters = sslParameters.apply {
                serverNames = emptyList()
            }
        }
        else -> this
    }

    private val socketFactory: SSLSocketFactory = SSLContext.getDefault().socketFactory

    override fun createSocket(socket: Socket?, host: String?, port: Int, autoClose: Boolean): Socket? =
        socketFactory.createSocket(socket, host, port, autoClose).setServerNames()

    override fun createSocket(host: String?, port: Int): Socket? =
        socketFactory.createSocket(host, port).setServerNames()

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket? =
        socketFactory.createSocket(host, port, localHost, localPort).setServerNames()

    override fun createSocket(host: InetAddress?, port: Int): Socket? =
        socketFactory.createSocket(host, port).setServerNames()

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket? =
        socketFactory.createSocket(address, port, localAddress, localPort).setServerNames()

    override fun getDefaultCipherSuites(): Array<String> = emptyArray()

    override fun getSupportedCipherSuites(): Array<String> = emptyArray()
}
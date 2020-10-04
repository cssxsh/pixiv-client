package xyz.cssxsh.pixiv.client

import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

object RubySSLSocketFactory : SSLSocketFactory() {

    private fun Socket.setServerNames(): Socket = when(this) {
        is SSLSocket -> apply {
            enabledProtocols = supportedProtocols
            enabledCipherSuites = supportedCipherSuites
            sslParameters = sslParameters.apply {
                serverNames = emptyList()
            }
            // println("Address: ${inetAddress.hostAddress}, \"serverNames: ${sslParameters.serverNames}, Protocols: ${sslParameters.protocols.map { toString() }}.")
        }
        else -> this
    }

    override fun createSocket(socket: Socket?, host: String?, port: Int, autoClose: Boolean): Socket? = socket?.let {
        getDefault().createSocket(it.inetAddress, port).setServerNames()
    }

    override fun createSocket(host: String?, port: Int): Socket? =
        null // getDefault().createSocket(host, port).setServerNames()

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket? =
        null // getDefault().createSocket(host, port, localHost, localPort).setServerNames()

    override fun createSocket(host: InetAddress?, port: Int): Socket? =
        null // getDefault().createSocket(host, port).setServerNames()

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket? =
        null // getDefault().createSocket(address, port, localAddress, localPort).setServerNames()

    override fun getDefaultCipherSuites(): Array<String> = emptyArray()

    override fun getSupportedCipherSuites(): Array<String> = emptyArray()
}
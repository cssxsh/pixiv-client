package xyz.cssxsh.pixiv.client

import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

object RubySSLSocketFactory : SSLSocketFactory() {

    private fun Socket?.setServerNames(): Socket? = when(this) {
        is SSLSocket -> apply {
            enabledProtocols = supportedProtocols
            enabledCipherSuites = supportedCipherSuites
            sslParameters.serverNames = emptyList()
            // ("Address: ${inetAddress.hostAddress}, Protocol: ${session.protocol}, PeerHost: ${session.peerHost}, CipherSuite: ${session.cipherSuite}.")
        }
        else -> this
    }

    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket? = s?.let {
        // TODO autoClose
        // if(autoClose) { it.close() }
        getDefault().createSocket(it.inetAddress, port).setServerNames()
    } ?: getDefault().createSocket(host, port).setServerNames()

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
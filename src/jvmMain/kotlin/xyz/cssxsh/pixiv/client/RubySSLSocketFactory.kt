package xyz.cssxsh.pixiv.client

import java.net.InetAddress
import java.net.Socket
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

object RubySSLSocketFactory : SSLSocketFactory() {

    private fun Socket?.setProtocols(): SSLSocket = (this as SSLSocket).apply {
        enabledProtocols = supportedProtocols
    }

    override fun createSocket(s: Socket?, host: String?, port: Int, autoClose: Boolean): Socket? = s?.let {
        if (autoClose) it.close()
        getDefault().createSocket(it.inetAddress, port).setProtocols()
    }

    override fun createSocket(host: String?, port: Int): Socket? =
        getDefault().createSocket(host, port).setProtocols()

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket? =
        getDefault().createSocket(host, port, localHost, localPort).setProtocols()

    override fun createSocket(host: InetAddress?, port: Int): Socket? =
        getDefault().createSocket(host, port).setProtocols()

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket? =
        getDefault().createSocket(address, port, localAddress, localPort).setProtocols()

    override fun getDefaultCipherSuites(): Array<String> = emptyArray()

    override fun getSupportedCipherSuites(): Array<String> = emptyArray()
}
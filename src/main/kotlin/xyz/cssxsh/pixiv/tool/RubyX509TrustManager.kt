package xyz.cssxsh.pixiv.tool

import java.security.*
import java.security.cert.*
import javax.net.ssl.*

object RubyX509TrustManager : X509TrustManager {

    private val delegate: X509TrustManager by lazy {
        val factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        factory.init(null as KeyStore?)
        factory.trustManagers.filterIsInstance<X509TrustManager>().first()
    }

    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) = Unit

    override fun getAcceptedIssuers(): Array<X509Certificate> = delegate.acceptedIssuers
}
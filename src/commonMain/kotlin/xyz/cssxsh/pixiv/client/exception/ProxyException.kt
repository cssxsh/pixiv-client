package xyz.cssxsh.pixiv.client.exception

class ProxyException(proxyUrl: String): IllegalArgumentException("Proxy: $proxyUrl Error!")
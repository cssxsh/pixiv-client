package xyz.cssxsh.pixiv.exception

class ProxyException(proxyUrl: String): IllegalArgumentException("Proxy: $proxyUrl Error!")
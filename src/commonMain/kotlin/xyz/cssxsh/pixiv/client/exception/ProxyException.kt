package xyz.cssxsh.pixiv.client.exception

class ProxyException(proxyUrl: String): Exception("Proxy: $proxyUrl Error!")
package xyz.cssxsh.pixiv.exception

import io.ktor.http.*

class ProxyException(proxy: Url): IllegalArgumentException("Proxy: $proxy Error!")
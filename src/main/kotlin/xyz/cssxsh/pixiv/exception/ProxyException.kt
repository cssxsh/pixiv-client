package xyz.cssxsh.pixiv.exception

import io.ktor.http.*

public class ProxyException(proxy: Url) : IllegalArgumentException("Proxy: $proxy Error!")
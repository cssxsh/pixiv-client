package xyz.cssxsh.pixiv.exception

import xyz.cssxsh.pixiv.web.*

public class WebApiException(public val result: WebApiResult) : IllegalArgumentException() {
    override val message: String get() = result.message
}
package xyz.cssxsh.pixiv.exception

import xyz.cssxsh.pixiv.web.*

class WebApiException(val result: WebApiResult) : IllegalArgumentException() {
    override val message: String get() = result.message
}
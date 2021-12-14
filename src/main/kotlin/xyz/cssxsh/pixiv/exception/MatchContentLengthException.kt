package xyz.cssxsh.pixiv.exception

import io.ktor.client.statement.*
import io.ktor.util.*

class MatchContentLengthException(response: HttpResponse) : IllegalStateException() {
    init {
        response.content.cancel(this)
    }

    override val message: String = "Not Match ContentLength $response ${response.headers.toMap()}"
}
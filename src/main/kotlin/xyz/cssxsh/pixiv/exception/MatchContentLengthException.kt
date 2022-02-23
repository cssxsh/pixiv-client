package xyz.cssxsh.pixiv.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.util.*

public class MatchContentLengthException(response: HttpResponse) :
    ResponseException(response, "<not match content length>") {
    init {
        response.content.cancel(this)
    }

    override val message: String = "Not Match ContentLength $response ${response.headers.toMap()}"
}
package xyz.cssxsh.pixiv.exception

import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.coroutines.*

public class MatchContentLengthException(
    response: HttpResponse
) : ResponseException(response, "<not match content length>") {

    init {
        response.call.cancel(super.message!!)
    }

    override val message: String = "Not Match ContentLength $response ${response.headers.toMap()}"
}

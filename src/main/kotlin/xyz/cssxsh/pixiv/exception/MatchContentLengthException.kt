package xyz.cssxsh.pixiv.exception

import io.ktor.client.statement.*
import io.ktor.util.*
import io.ktor.utils.io.errors.*

class MatchContentLengthException(val response: HttpResponse): IOException() {
    override val message: String = "Not Match ContentLength $response ${response.headers.toMap()}"
}
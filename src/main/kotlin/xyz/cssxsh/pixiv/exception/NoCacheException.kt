package xyz.cssxsh.pixiv.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.util.*

class NoCacheException(response: HttpResponse) : ResponseException(response, "<no cache>") {
    init {
        response.content.cancel(this)
    }

    override val message: String = "No Cache $response ${response.headers.toMap()}"
}
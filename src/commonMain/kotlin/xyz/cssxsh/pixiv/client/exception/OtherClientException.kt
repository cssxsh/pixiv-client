package xyz.cssxsh.pixiv.client.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*

class OtherClientException(response: HttpResponse): ResponseException(response) {
    override val message: String = response.run {
        "OtherException(url: ${call.request.url} invalid: ${status}, headers: ${headers}})"
    }
}
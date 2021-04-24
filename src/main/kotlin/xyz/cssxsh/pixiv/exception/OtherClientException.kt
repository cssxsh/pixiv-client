package xyz.cssxsh.pixiv.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

class OtherClientException(response: HttpResponse, content: String) : ResponseException(response, content) {

    val json = Json.parseToJsonElement(content)

    override fun toString(): String = response.run {
        "OtherException(url: ${call.request.url}, invalid: ${status}, headers: ${request.headers.toMap()}, json: ${json})"
    }
}
package xyz.cssxsh.pixiv.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.json.Json

class PublicApiException(response: HttpResponse, content: String) : ResponseException(response, content) {

    val json: PublicError = Json.decodeFromString(PublicError.serializer(), content)

    override val message: String = json.errors.toString()

    override fun toString(): String = response.run {
        "ApiException(url: ${call.request.url}, invalid: ${status}, header: ${request.headers.toMap()}, error: ${json})"
    }
}
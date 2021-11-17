package xyz.cssxsh.pixiv.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.json.*

class AuthException(response: HttpResponse, content: String) : ResponseException(response, content) {

    val json: AuthError = Json.decodeFromString(AuthError.serializer(), content)

    override val message: String = json.errors["system"]?.message ?: json.error

    override fun toString(): String = with(response) {
        "AuthException(url: ${call.request.url}, invalid: ${status}, header: ${request.headers.toMap()}, error: ${json})"
    }
}
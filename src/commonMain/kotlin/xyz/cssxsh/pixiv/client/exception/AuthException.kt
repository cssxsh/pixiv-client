package xyz.cssxsh.pixiv.client.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import xyz.cssxsh.pixiv.data.AuthError

class AuthException(response: HttpResponse, val authError: AuthError) : ResponseException(response) {
    constructor(response: HttpResponse, json: String) : this(
        response,
        Json.decodeFromString(AuthError.serializer(), json)
    )

    override val message: String? = response.run {
        "AuthException(url: ${call.request.url} header: ${request.headers} invalid: ${status}, error: ${authError})"
    }
}
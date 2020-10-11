package xyz.cssxsh.pixiv.client.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.data.AuthError

class OtherClientException(response: HttpResponse, val jsonElement: JsonElement): ResponseException(response) {
    constructor(response: HttpResponse, json: String) : this(
        response,
        Json.parseToJsonElement(json)
    )

    override val message: String = response.run {
        "OtherException(url: ${call.request.url}, invalid: ${status}, headers: ${request.headers.toMap()}, json: ${jsonElement})"
    }
}
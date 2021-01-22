package xyz.cssxsh.pixiv.client.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import xyz.cssxsh.pixiv.data.ApiError

class ApiException(response: HttpResponse, content: String) : ResponseException(response, content) {

    val json: ApiError = Json.decodeFromString(ApiError.serializer(), content)

    override val message: String = json.error.message

    override fun toString(): String = response.run {
        "ApiException(url: ${call.request.url}, invalid: ${status}, header: ${request.headers.toMap()}, error: ${json})"
    }
}
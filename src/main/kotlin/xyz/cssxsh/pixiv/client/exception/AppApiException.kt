package xyz.cssxsh.pixiv.client.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import xyz.cssxsh.pixiv.data.AppApiError

class AppApiException(response: HttpResponse, content: String) : ResponseException(response, content) {

    val json: AppApiError = Json.decodeFromString(AppApiError.serializer(), content)

    override val message: String = json.error.message

    override fun toString(): String = response.run {
        "ApiException(url: ${call.request.url}, invalid: ${status}, header: ${request.headers.toMap()}, error: ${json})"
    }
}
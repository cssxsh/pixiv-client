package xyz.cssxsh.pixiv.exception

import io.ktor.client.plugins.*
import io.ktor.client.statement.*
import io.ktor.util.*
import kotlinx.serialization.json.*

public class AppApiException(response: HttpResponse, content: String) : ResponseException(response, content) {

    public val json: AppApiError = Json.decodeFromString(AppApiError.serializer(), content)

    override val message: String = json.error.message.ifBlank { json.error.userMessage }

    override fun toString(): String = with(response) {
        "ApiException(url: ${call.request.url}, invalid: ${status}, header: ${request.headers.toMap()}, error: ${json})"
    }
}
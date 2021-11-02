package xyz.cssxsh.pixiv.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*

val TransferExceptionHandler: CallExceptionHandler = { cause ->
    if (cause is ClientRequestException) {
        val content = cause.response.readText()

        cause.runCatching {
            AppApiException(response, content)
        }.onSuccess {
            throw it
        }

        cause.runCatching {
            AppApiException(response, content)
        }.onSuccess {
            throw it
        }

        cause.runCatching {
            AppApiException(response, content)
        }.onSuccess {
            throw it
        }
    }
}
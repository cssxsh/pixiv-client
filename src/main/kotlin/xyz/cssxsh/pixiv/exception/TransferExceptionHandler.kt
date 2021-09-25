package xyz.cssxsh.pixiv.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*

val TransferExceptionHandler: CallExceptionHandler = { cause ->
    if (cause is ClientRequestException) {
        cause.response.readText().let { content ->
            runCatching {
                AppApiException(cause.response, content)
            }.onSuccess {
                throw it
            }

            runCatching {
                AuthException(cause.response, content)
            }.onSuccess {
                throw it
            }

            runCatching {
                OtherClientException(cause.response, content)
            }.onSuccess {
                throw it
            }
        }
    }
}
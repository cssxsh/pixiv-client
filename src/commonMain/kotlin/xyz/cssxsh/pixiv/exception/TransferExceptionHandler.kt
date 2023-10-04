package xyz.cssxsh.pixiv.exception

import io.ktor.client.plugins.*
import io.ktor.client.statement.*

public val TransferExceptionHandler: CallRequestExceptionHandler = { cause, _ ->
    if (cause is ClientRequestException) {
        val content = cause.response.bodyAsText()
        val list = listOf(::AppApiException, ::AuthException, ::OtherClientException)

        for (builder in list) {
            throw try {
                builder(cause.response, content)
            } catch (_: Throwable) {
                continue
            }
        }
    }
}
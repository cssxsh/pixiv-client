package xyz.cssxsh.pixiv.exception

import io.ktor.client.features.*
import io.ktor.client.statement.*

public val TransferExceptionHandler: CallExceptionHandler = { cause ->
    if (cause is ClientRequestException) {
        val content = cause.response.readText()
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
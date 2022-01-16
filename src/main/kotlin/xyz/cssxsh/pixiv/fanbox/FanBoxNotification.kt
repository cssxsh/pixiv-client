package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

class FanBoxNotification(val client: PixivWebClient) {
    companion object {
        internal const val GET_SETTINGS = "https://api.fanbox.cc/notification.getSettings"

        internal const val UPDATE_SETTINGS = "https://api.fanbox.cc/notification.updateSettings"
    }

    suspend fun getSettings(): Map<String, Boolean> {
        return client.ajax(api = GET_SETTINGS) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    suspend fun updateSettings(type: String, value: Boolean) {
        val metadata = getMetaData()

        client.ajax<Map<String, Boolean>?>(api = UPDATE_SETTINGS) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
            header(HttpHeaders.XCsrfToken, metadata.csrfToken)

            method = HttpMethod.Post

            body = mapOf(
                "type" to type,
                "value" to if (value) "1" else "0"
            )

            contentType(ContentType.Application.Json)
        }
    }
}
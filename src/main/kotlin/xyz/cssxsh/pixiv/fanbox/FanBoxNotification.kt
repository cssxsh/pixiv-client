package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

public class FanBoxNotification(override val client: PixivWebClient) : FanBoxApi() {
    public companion object {
        internal const val GET_SETTINGS = "https://api.fanbox.cc/notification.getSettings"

        internal const val UPDATE_SETTINGS = "https://api.fanbox.cc/notification.updateSettings"
    }

    public suspend fun getSettings(): Map<Notification, Boolean> {
        return client.ajax(api = GET_SETTINGS) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    public suspend fun updateSettings(type: Notification, value: Boolean) {
        val metadata = getMetaData()

        client.ajax<String?>(api = UPDATE_SETTINGS) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
            header(HttpHeaders.XCsrfToken, metadata.csrfToken)

            method = HttpMethod.Post

            setBody(buildJsonObject {
                put("type", type.name.lowercase())
                put("value", if (value) "1" else "0")
            })
            contentType(ContentType.Application.Json)
        }
    }
}
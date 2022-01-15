package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.PixivWebClient
import xyz.cssxsh.pixiv.web.ajax

class FanBoxNotification(val client: PixivWebClient) {
    companion object {
        internal const val GET_SETTINGS = "https://api.fanbox.cc/notification.getSettings"
    }

    suspend fun getSettings(): Map<String, Boolean> {
        return client.ajax(api = GET_SETTINGS) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }
}
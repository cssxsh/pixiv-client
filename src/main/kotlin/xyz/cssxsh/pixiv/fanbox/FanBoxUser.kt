package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

class FanBoxUser(val client: PixivWebClient) {
    companion object {
        internal const val COUNT_UNREAD_MESSAGES = "https://api.fanbox.cc/user.countUnreadMessages"
    }

    suspend fun countUnreadMessages(): Int {
        return client.ajax(api = COUNT_UNREAD_MESSAGES) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }
}
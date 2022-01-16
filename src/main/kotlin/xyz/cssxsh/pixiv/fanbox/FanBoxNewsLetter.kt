package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

class FanBoxNewsLetter(override val client: PixivWebClient) : FanBoxApi() {
    companion object {
        internal const val COUNT_UNREAD = "https://api.fanbox.cc/newsletter.countUnread"
    }

    suspend fun countUnread(): Int {
        return client.ajax(api = COUNT_UNREAD) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }
}
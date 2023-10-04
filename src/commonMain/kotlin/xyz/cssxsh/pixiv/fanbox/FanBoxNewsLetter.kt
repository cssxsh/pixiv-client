package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

public class FanBoxNewsLetter(override val client: PixivWebClient) : FanBoxApi() {
    public companion object {
        internal const val COUNT_UNREAD = "https://api.fanbox.cc/newsletter.countUnread"
    }

    public suspend fun countUnread(): Int {
        return client.ajax(api = COUNT_UNREAD) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }
}
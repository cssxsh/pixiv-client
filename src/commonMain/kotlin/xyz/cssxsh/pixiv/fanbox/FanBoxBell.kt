package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

public class FanBoxBell(override val client: PixivWebClient) : FanBoxApi() {
    public companion object {
        internal const val COUNT_UNREAD = "https://api.fanbox.cc/bell.countUnread"
    }

    @Serializable
    public data class CountUnread(
        @SerialName("count")
        val count: Int
    )

    public suspend fun countUnread(): Int {
        return client.ajax<CountUnread>(api = COUNT_UNREAD) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }.count
    }
}
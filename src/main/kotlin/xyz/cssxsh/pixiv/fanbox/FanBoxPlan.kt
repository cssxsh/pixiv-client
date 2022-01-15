package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

class FanBoxPlan(val client: PixivWebClient) {
    companion object {
        internal const val LIST_CREATOR = "https://api.fanbox.cc/plan.listCreator"

        internal const val LIST_SUPPORTING = "https://api.fanbox.cc/plan.listSupportinstr"
    }

    suspend fun listCreator(creatorId: String): List<CreatorDetail> {
        return client.ajax(api = LIST_CREATOR) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    suspend fun listSupportinstr(): List<CreatorDetail> {
        return client.ajax(api = LIST_SUPPORTING) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }
}
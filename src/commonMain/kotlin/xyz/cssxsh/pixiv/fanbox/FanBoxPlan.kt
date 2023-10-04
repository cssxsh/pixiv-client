package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

public class FanBoxPlan(override val client: PixivWebClient) : FanBoxApi() {
    public companion object {
        internal const val LIST_CREATOR = "https://api.fanbox.cc/plan.listCreator"

        internal const val LIST_SUPPORTING = "https://api.fanbox.cc/plan.listSupporting"
    }

    public suspend fun listCreator(creatorId: String): List<PlanInfo> {
        return client.ajax(api = LIST_CREATOR) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("creatorId", creatorId)
        }
    }

    public suspend fun listCreator(userId: Long): List<PlanInfo> {
        return client.ajax(api = LIST_CREATOR) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")

            parameter("userId", userId)
        }
    }

    public suspend fun listSupporting(): List<PlanInfo> {
        return client.ajax(api = LIST_SUPPORTING) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }
}
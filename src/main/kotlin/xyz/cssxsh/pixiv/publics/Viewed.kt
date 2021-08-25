package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*

suspend fun PixivAppClient.postViewedWorks(
    pids:  List<Long>,
    url: String = ME_VIEWED_WORKS,
): JsonElement = useHttpClient { client ->
    client.post(url) {
        header(HttpHeaders.Referrer, REFERER)

        parameter("ids", pids.joinToString(","))
    }
}
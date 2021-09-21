package xyz.cssxsh.pixiv.web

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*
import java.util.*

private fun api(uid: Long, type: String) = "https://www.pixiv.net/ajax/user/${uid}/profile/$type"

private fun referer(uid: Long) = "https://www.pixiv.net/users/${uid}"

suspend fun UseHttpClient.getProfileAll(
    uid: Long,
    locale: Locale = Locale.CHINA,
): ProfileAll = useHttpClient { client ->
    client.get<WebApiResult>(api(uid = uid, type = "all")) {
        header(HttpHeaders.Referrer, referer(uid = uid))

        parameter("lang", locale.language)
    }.value()
}

suspend fun UseHttpClient.getProfileTop(
    uid: Long,
    locale: Locale = Locale.CHINA,
): JsonObject = useHttpClient { client ->
    client.get<WebApiResult>(api(uid = uid, type = "top")) {
        header(HttpHeaders.Referrer, referer(uid = uid))

        parameter("lang", locale.language)
    }.value()
}
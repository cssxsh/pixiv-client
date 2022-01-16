package xyz.cssxsh.pixiv.fanbox

import io.ktor.http.*
import xyz.cssxsh.pixiv.*

internal val HttpHeaders.XCsrfToken by lazy { "X-Csrf-Token" }

val PixivWebClient.bell get() = FanBoxBell(client = this)

val PixivWebClient.creator get() = FanBoxCreator(client = this)

val PixivWebClient.newsletter get() = FanBoxNewsLetter(client = this)

val PixivWebClient.notification get() = FanBoxNotification(client = this)

val PixivWebClient.payment get() = FanBoxPayment(client = this)

val PixivWebClient.plan get() = FanBoxPlan(client = this)

val PixivWebClient.post get() = FanBoxPost(client = this)

val PixivWebClient.tag get() = FanBoxTag(client = this)

val PixivWebClient.user get() = FanBoxUser(client = this)

val PostDetail.url get() = "https://www.fanbox.cc/@${creatorId}/posts/${id}"
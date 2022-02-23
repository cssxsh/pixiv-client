package xyz.cssxsh.pixiv.fanbox

import io.ktor.http.*
import xyz.cssxsh.pixiv.*

internal val HttpHeaders.XCsrfToken by lazy { "X-Csrf-Token" }

public val PixivWebClient.bell: FanBoxBell get() = FanBoxBell(client = this)

public val PixivWebClient.creator: FanBoxCreator get() = FanBoxCreator(client = this)

public val PixivWebClient.newsletter: FanBoxNewsLetter get() = FanBoxNewsLetter(client = this)

public val PixivWebClient.notification: FanBoxNotification get() = FanBoxNotification(client = this)

public val PixivWebClient.payment: FanBoxPlan get() = FanBoxPlan(client = this)

public val PixivWebClient.plan: FanBoxPlan get() = FanBoxPlan(client = this)

public val PixivWebClient.post: FanBoxPost get() = FanBoxPost(client = this)

public val PixivWebClient.tag: FanBoxTag get() = FanBoxTag(client = this)

public val PixivWebClient.user: FanBoxUser get() = FanBoxUser(client = this)

public val PostDetail.url: String get() = "https://www.fanbox.cc/@${creatorId}/posts/${id}"
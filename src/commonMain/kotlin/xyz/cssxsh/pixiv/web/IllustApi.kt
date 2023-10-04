package xyz.cssxsh.pixiv.web

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.FollowType
import xyz.cssxsh.pixiv.PixivWebClient

private fun referer(pid: Long) = "https://www.pixiv.net/artwork/${pid}"

private fun illust(pid: Long) = "https://www.pixiv.net/ajax/illust/${pid}"

public suspend fun PixivWebClient.ajaxIllust(
    pid: Long,
    offset: Long,
    limit: Int,
    rest: FollowType = FollowType.SHOW,
    tag: String = "",
    locale: String = "zh",
): UserFollowing = ajax(illust(pid = pid)) {
    header(HttpHeaders.Referrer, referer(pid = pid))

    parameter("offset", offset)
    parameter("limit", limit)
    parameter("rest", rest)
    parameter("tag", tag)
    parameter("lang", locale)
}
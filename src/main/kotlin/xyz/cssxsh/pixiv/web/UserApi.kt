package xyz.cssxsh.pixiv.web

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*
import java.util.*

private fun referer(uid: Long) = "https://www.pixiv.net/users/${uid}"

private fun profile(uid: Long, type: String) = "https://www.pixiv.net/ajax/user/${uid}/profile/$type"

suspend fun PixivWebClient.ajaxProfileAll(
    uid: Long,
    locale: Locale = Locale.CHINA,
): UserProfileAll = web(profile(uid = uid, type = "all")) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    parameter("lang", locale.language)
}

suspend fun PixivWebClient.ajaxProfileTop(
    uid: Long,
    locale: Locale = Locale.CHINA,
): UserProfileTop = web(profile(uid = uid, type = "top")) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    parameter("lang", locale.language)
}

suspend fun PixivWebClient.ajaxProfileIllusts(
    uid: Long,
    ids: Set<Long>,
    category: CategoryType,
    locale: Locale = Locale.CHINA,
): UserProfileIllusts = web(profile(uid = uid, type = "illusts")) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    for (id in ids) {
        parameter("ids[]", id)
    }
    parameter("work_category", category)
    parameter("is_first_page", 1)
    parameter("lang", locale.language)
}

private fun following(uid: Long) = "https://www.pixiv.net/ajax/user/${uid}/following"

suspend fun PixivWebClient.ajaxFollowing(
    uid: Long,
    offset: Long,
    limit: Int,
    rest: FollowType = FollowType.SHOW,
    tag: String = "",
    locale: Locale = Locale.CHINA,
): UserFollowing = web(following(uid = uid)) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    parameter("offset", offset)
    parameter("limit", limit)
    parameter("rest", rest)
    parameter("tag", tag)
    parameter("lang", locale.language)
}

private fun bookmarks(uid: Long, type: String) = "https://www.pixiv.net/ajax/user/${uid}/${type}/bookmarks"

suspend fun PixivWebClient.ajaxBookmarks(
    uid: Long,
    offset: Long,
    limit: Int,
    rest: FollowType = FollowType.SHOW,
    tag: String = "",
    locale: Locale = Locale.CHINA,
): UserBookmarks<WebIllust> = web(bookmarks(uid = uid, type = "illust")) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    parameter("offset", offset)
    parameter("limit", limit)
    parameter("rest", rest)
    parameter("tag", tag)
    parameter("lang", locale.language)
}

// https://www.pixiv.net/ajax/user/478993/illusts/bookmark/tags?lang=zh
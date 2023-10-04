package xyz.cssxsh.pixiv.web

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.CategoryType
import xyz.cssxsh.pixiv.FollowType
import xyz.cssxsh.pixiv.PixivWebClient

private fun referer(uid: Long) = "https://www.pixiv.net/users/${uid}"

private fun profile(uid: Long, type: String) = "https://www.pixiv.net/ajax/user/${uid}/profile/$type"

public suspend fun PixivWebClient.ajaxProfileAll(
    uid: Long,
    locale: String = "zh",
): UserProfileAll = ajax(profile(uid = uid, type = "all")) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    parameter("lang", locale)
}

public suspend fun PixivWebClient.ajaxProfileTop(
    uid: Long,
    locale: String = "zh",
): UserProfileTop = ajax(profile(uid = uid, type = "top")) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    parameter("lang", locale)
}

public suspend fun PixivWebClient.ajaxProfileIllusts(
    uid: Long,
    ids: Set<Long>,
    category: CategoryType,
    locale: String = "zh",
): UserProfileIllusts = ajax(profile(uid = uid, type = "illusts")) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    for (id in ids) {
        parameter("ids[]", id)
    }
    parameter("work_category", category)
    parameter("is_first_page", 1)
    parameter("lang", locale)
}

private fun following(uid: Long) = "https://www.pixiv.net/ajax/user/${uid}/following"

public suspend fun PixivWebClient.ajaxFollowing(
    uid: Long,
    offset: Long,
    limit: Int,
    rest: FollowType = FollowType.SHOW,
    tag: String = "",
    locale: String = "zh",
): UserFollowing = ajax(following(uid = uid)) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    parameter("offset", offset)
    parameter("limit", limit)
    parameter("rest", rest)
    parameter("tag", tag)
    parameter("lang", locale)
}

private fun bookmarks(uid: Long, type: String) = "https://www.pixiv.net/ajax/user/${uid}/${type}/bookmarks"

public suspend fun PixivWebClient.ajaxBookmarks(
    uid: Long,
    offset: Long,
    limit: Int,
    rest: FollowType = FollowType.SHOW,
    tag: String = "",
    locale: String = "zh",
): UserBookmarks<WebIllust> = ajax(bookmarks(uid = uid, type = "illust")) {
    header(HttpHeaders.Referrer, referer(uid = uid))

    parameter("offset", offset)
    parameter("limit", limit)
    parameter("rest", rest)
    parameter("tag", tag)
    parameter("lang", locale)
}

// https://www.pixiv.net/ajax/user/478993/illusts/bookmark/tags?lang=zh
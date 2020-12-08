package xyz.cssxsh.pixiv.api.public

object PublicApi {
    const val ROOT_PATH = "https://public-api.secure.pixiv.net"
    const val REFERER = "http://spapi.pixiv-app.net/"

    // favorite
    const val ME_FAVORITE_WORKS = "${ROOT_PATH}/v1/me/favorite_works"
    const val ME_FAVORITE_USERS = "${ROOT_PATH}/v1/me/favorite-users"
    // following
    const val ME_FOLLOWING_WORKS = "${ROOT_PATH}/v1/me/following/works"
    // viewed
    const val ME_VIEWED_WORKS = "${ROOT_PATH}/v1/me/viewed_works"
    // friends
    const val ME_FRIENDS_WORKS = "${ROOT_PATH}/v1/me/friends/works"
    // feeds
    const val ME_FEEDS = "${ROOT_PATH}/v1/me/feeds"
    // ranking
    val RANKING: (RankingType) -> String = { type -> "${ROOT_PATH}/v1/ranking/$type" }
    enum class RankingType { ALL, ILLUST, MANGA, UGOIRA }
    // search
    const val SEARCH_WORKS = "${ROOT_PATH}/v1/search/works"
    // users
    val USERS_FAVORITE_WORKS: (Long) -> String = { uid -> "${ROOT_PATH}/v1/users/$uid/favorite_works" }
    val USERS_WORKS: (Long) -> String = { uid -> "${ROOT_PATH}/v1/users/$uid/works" }
    // works
    const val WORKS = "${ROOT_PATH}/v1/works"
    val WORKS_BY_PID: (Long) -> String = { pid -> "${ROOT_PATH}/v1/works/$pid" }
}
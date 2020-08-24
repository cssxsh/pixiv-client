package xyz.cssxsh.pixiv.client.api.papi

internal object PublicApiUrls {
    private const val host = "public-api.secure.pixiv.net"
    // ranking
    const val rankingAll = "https://$host/v1/ranking/all"
    // search
    const val searchWorks = "https://$host/v1/search/works"
    // users
    val usersFavoriteWorks: (Long) -> String = { uid -> "https://$host/v1/users/$uid/favorite_works" }
    val usersWorks: (Long) -> String = { uid -> "https://$host/v1/users/$uid/works" }
    // me
    const val meFollowingWorks = "https://$host/v1/me/following/works"
    const val meFavoriteWorks = "https://$host/v1/me/favorite_works"
    const val meFavoriteUsers = "https://$host/v1/me/favorite-users"
    const val meViewedWorks = "https://$host/v1/me/viewed_works"
    const val meFriendsWorks = "https://$host/v1/me/friends/works"
    const val meFeeds = "https://$host/v1/me/feeds"
    // works
    const val works = "https://$host/v1/works"
    val worksByPid: (Long) -> String = { pid -> "https://$host/v1/works/$pid" }
}
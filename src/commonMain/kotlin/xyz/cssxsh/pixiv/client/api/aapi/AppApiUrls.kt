package xyz.cssxsh.pixiv.client.api.aapi

internal object AppApiUrls {
    private const val rootPath = "https://app-api.pixiv.net"
    // search
    const val searchIllust = "$rootPath/v1/search/illust"
    const val searchNovel = "$rootPath/v1/search/novel"
    const val searchAutoComplete = "$rootPath/v1/search/autocomplete"
    // trending-tags
    const val trendingTagsIllust = "$rootPath/v1/trending-tags/illust"
    const val trendingTagsNovel =  "$rootPath/v1/trending-tags/novel"
    // ugoira
    const val ugoiraMetadata = "$rootPath/v1/ugoira/metadata"
    // user
    const val userIllusts = "$rootPath/v1/user/illusts"
    const val userBookmarksIllust = "$rootPath/v1/user/bookmarks/illust"
    const val userBookmarksNovel = "$rootPath/v1/user/bookmarks/novel"
    const val userRecommended = "$rootPath/v1/user/recommended"
    const val userFollower = "$rootPath/v1/user/follower"
    const val userMypixiv = "$rootPath/v1/user/mypixiv"
    const val userList = "$rootPath/v2/user/list"
    const val userDetail = "$rootPath/v1/user/detail"
    const val userFollowing = "$rootPath/v1/user/following"
    const val userFollowAdd = "$rootPath/v1/user/follow/add"
    const val userFollowDelete  = "$rootPath/v1/user/follow/delete"
    // illust
    const val illustFollow = "$rootPath/v2/illust/follow"
    const val illustBookmarkAdd = "$rootPath/v2/illust/bookmark/add"
    const val illustBookmarkDelete  = "$rootPath/v1/illust/bookmark/delete"
    const val illustNew = "$rootPath/v1/illust/new"
    const val illustMypixiv = "$rootPath/v1/illust/mypixiv"
    const val illustRanking = "$rootPath/v1/illust/ranking"
    const val illustRecommended = "$rootPath/v1/illust/recommended"
    const val illustRecommendedNologin = "$rootPath/v1/illust/recommended-nologin"
    // novel
    const val novelFollow = "$rootPath/v2/novel/follow"
    const val novelNew = "$rootPath/v2/novel/new"
    const val novelMypixiv = "$rootPath/v2/novel/mypixiv"
    const val novelMarkers  = "$rootPath/v2/novel/markers"
}
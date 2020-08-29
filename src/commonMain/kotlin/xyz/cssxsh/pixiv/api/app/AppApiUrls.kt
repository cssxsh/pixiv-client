package xyz.cssxsh.pixiv.api.app

object AppApiUrls {
    const val rootPath = "https://app-api.pixiv.net"

    // illust
    const val illustBookmarkAdd = "${rootPath}/v2/illust/bookmark/add"
    const val illustBookmarkDelete = "${rootPath}/v1/illust/bookmark/delete"
    const val illustBookmarkDetail = "${rootPath}/v2/illust/bookmark/detail"
    const val illustComments = "${rootPath}/v2/illust/comments"
    const val illustDetail = "${rootPath}/v1/illust/detail"
    const val illustFollow = "${rootPath}/v2/illust/follow"
    const val illustMyPixiv = "${rootPath}/v2/illust/mypixiv"
    const val illustNew = "${rootPath}/v1/illust/new"
    const val illustRanking = "${rootPath}/v1/illust/ranking"
    const val illustRecommended = "${rootPath}/v1/illust/recommended"
    // const val illustRecommendedNoLogin = "${rootPath}/v2/illust/recommended-noLogin"
    const val illustRelated = "${rootPath}/v2/illust/related"

    // search
    const val searchIllust = "${rootPath}/v1/search/illust"
    const val searchNovel = "${rootPath}/v1/search/novel"
    const val searchAutoComplete = "${rootPath}/v1/search/autocomplete"

    // trending-tags
    const val trendingTagsIllust = "${rootPath}/v1/trending-tags/illust"
    const val trendingTagsNovel = "${rootPath}/v1/trending-tags/novel"

    // ugoira
    const val ugoiraMetadata = "${rootPath}/v1/ugoira/metadata"

    // user
    const val userBookmarksIllust = "${rootPath}/v1/user/bookmarks/illust"
    const val userBookmarksNovel = "${rootPath}/v1/user/bookmarks/novel"
    const val userBlacklist = "${rootPath}/v2/user/list"
    const val userBookmarksTagsIllust = "${rootPath}/v1/user/bookmark-tags/illust"
    const val userBookmarksTagsNovel = "${rootPath}/v1/user/bookmark-tags/novel"
    const val userDetail = "${rootPath}/v1/user/detail"
    const val userFollowAdd = "${rootPath}/v1/user/follow/add"
    const val userFollowDelete = "${rootPath}/v1/user/follow/delete"
    const val userFollower = "${rootPath}/v1/user/follower"
    const val userFollowing = "${rootPath}/v1/user/following"
    const val userIllusts = "${rootPath}/v1/user/illusts"
    const val userMyPixiv = "${rootPath}/v1/user/mypixiv"
    const val userRecommended = "${rootPath}/v1/user/recommended"

    // novel
    const val novelFollow = "${rootPath}/v2/novel/follow"
    const val novelNew = "${rootPath}/v2/novel/new"
    const val novelMyPixiv = "${rootPath}/v2/novel/myPixiv"
    const val novelMarkers = "${rootPath}/v2/novel/markers"

    // spotlight
    const val spotlightArticles = "${rootPath}/v1/spotlight/articles"
}
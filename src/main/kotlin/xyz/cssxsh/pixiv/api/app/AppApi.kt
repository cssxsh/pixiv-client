package xyz.cssxsh.pixiv.api.app

object AppApi {
    const val ROOT_PATH = "https://app-api.pixiv.net"
    const val PAGE_SIZE = 30L
    const val RELATED_OFFSET = 150L

    // Illust
    const val ILLUST_BOOKMARK_ADD =  "${ROOT_PATH}/v2/illust/bookmark/add"
    const val ILLUST_BOOKMARK_DELETE = "${ROOT_PATH}/v1/illust/bookmark/delete"
    const val ILLUST_BOOKMARK_DETAIL = "${ROOT_PATH}/v2/illust/bookmark/detail"
    const val ILLUST_COMMENTS = "${ROOT_PATH}/v2/illust/comments"
    const val ILLUST_DETAIL = "${ROOT_PATH}/v1/illust/detail"
    const val ILLUST_FOLLOW = "${ROOT_PATH}/v2/illust/follow"
    const val ILLUST_MYPIXIV = "${ROOT_PATH}/v2/illust/mypixiv"
    const val ILLUST_NEW = "${ROOT_PATH}/v1/illust/new"
    const val ILLUST_RANKING = "${ROOT_PATH}/v1/illust/ranking"
    const val ILLUST_RECOMMENDED = "${ROOT_PATH}/v1/illust/recommended"
    const val ILLUST_RELATED = "${ROOT_PATH}/v2/illust/related"
    // Novel
    const val NOVEL_BOOKMARK_ADD =  "${ROOT_PATH}/v2/novel/bookmark/add"
    const val NOVEL_BOOKMARK_DELETE = "${ROOT_PATH}/v1/novel/bookmark/delete"
    const val NOVEL_BOOKMARK_DETAIL = "${ROOT_PATH}/v2/novel/bookmark/detail"
    const val NOVEL_COMMENTS = "${ROOT_PATH}/v2/novel/comments"
    const val NOVEL_DETAIL = "${ROOT_PATH}/v2/novel/detail"
    const val NOVEL_FOLLOW = "${ROOT_PATH}/v2/novel/follow"
    const val NOVEL_MARKERS = "${ROOT_PATH}/v2/novel/markers"
    const val NOVEL_MYPIXIV = "${ROOT_PATH}/v2/novel/mypixiv"
    const val NOVEL_NEW = "${ROOT_PATH}/v2/novel/new"
    const val NOVEL_RANKING = "${ROOT_PATH}/v1/novel/ranking"
    const val NOVEL_RECOMMENDED = "${ROOT_PATH}/v1/novel/recommended"
    // Search
    const val SEARCH_AUTO_COMPLETE = "${ROOT_PATH}/v1/search/autocomplete"
    const val SEARCH_ILLUST = "${ROOT_PATH}/v1/search/illust"
    const val SEARCH_NOVEL = "${ROOT_PATH}/v1/search/novel"
    // Spotlight
    const val SPOTLIGHT_ARTICLES = "${ROOT_PATH}/v1/spotlight/articles"
    // Trending
    const val TRENDING_TAGS_ILLUST = "${ROOT_PATH}/v1/trending-tags/illust"
    const val TRENDING_TAGS_NOVEL = "${ROOT_PATH}/v1/trending-tags/novel"
    // Ugoira
    const val UGOIRA_METADATA = "${ROOT_PATH}/v1/ugoira/metadata"
    // User
    const val USER_BLACKLIST = "${ROOT_PATH}/v2/user/list"
    const val USER_BOOKMARKS_ILLUST = "${ROOT_PATH}/v1/user/bookmarks/illust"
    const val USER_BOOKMARKS_NOVEL = "${ROOT_PATH}/v1/user/bookmarks/novel"
    const val USER_BOOKMARKS_TAGS_ILLUST = "${ROOT_PATH}/v1/user/bookmark-tags/illust"
    const val USER_BOOKMARKS_TAGS_NOVEL = "${ROOT_PATH}/v1/user/bookmark-tags/novel"
    const val USER_DETAIL = "${ROOT_PATH}/v1/user/detail"
    const val USER_FOLLOWER = "${ROOT_PATH}/v1/user/follower"
    const val USER_FOLLOWING = "${ROOT_PATH}/v1/user/following"
    const val USER_FOLLOW_ADD = "${ROOT_PATH}/v1/user/follow/add"
    const val USER_FOLLOW_DELETE = "${ROOT_PATH}/v1/user/follow/delete"
    const val USER_ILLUSTS = "${ROOT_PATH}/v1/user/illusts"
    const val USER_MYPIXIV = "${ROOT_PATH}/v1/user/mypixiv"
    const val USER_RECOMMENDED = "${ROOT_PATH}/v1/user/recommended"
}
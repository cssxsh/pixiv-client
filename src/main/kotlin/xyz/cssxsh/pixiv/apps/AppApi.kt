package xyz.cssxsh.pixiv.apps


const val ROOT_PATH = "https://app-api.pixiv.net"
const val PAGE_SIZE = 30L
const val ARTICLE_PAGE_SIZE = 10L
const val FOLLOW_LIMIT = 6000L
const val RECOMMENDED_LIMIT = 5000L
const val RELATED_LIMIT = 150L
const val SEARCH_LIMIT = 5000L
const val TAG_LIMIT = 10L
const val BOOKMARK_TAG_LIMIT = 5000L

// Illust
const val ILLUST_BOOKMARK_ADD =  "${ROOT_PATH}/v2/illust/bookmark/add"
const val ILLUST_BOOKMARK_DELETE = "${ROOT_PATH}/v1/illust/bookmark/delete"
const val ILLUST_BOOKMARK_DETAIL = "${ROOT_PATH}/v2/illust/bookmark/detail"
const val ILLUST_BOOKMARK_USERS = "${ROOT_PATH}/v1/illust/bookmark/users?filter=for_android"
const val ILLUST_COMMENT_ADD = "${ROOT_PATH}/v1/illust/comment/add"
const val ILLUST_COMMENT_DELETE = "${ROOT_PATH}/v1/illust/comment/delete"
const val ILLUST_COMMENT_REPLIES = "${ROOT_PATH}/v2/illust/comment/replies"
const val ILLUST_COMMENTS = "${ROOT_PATH}/v3/illust/comments"
const val ILLUST_DELETE = "${ROOT_PATH}/v1/illust/delete"
const val ILLUST_DETAIL = "${ROOT_PATH}/v1/illust/detail"
const val ILLUST_FOLLOW = "${ROOT_PATH}/v2/illust/follow"
const val ILLUST_MYPIXIV = "${ROOT_PATH}/v2/illust/mypixiv"
const val ILLUST_NEW = "${ROOT_PATH}/v1/illust/new"
const val ILLUST_RANKING = "${ROOT_PATH}/v1/illust/ranking"
const val ILLUST_RECOMMENDED = "${ROOT_PATH}/v1/illust/recommended"
const val ILLUST_RELATED = "${ROOT_PATH}/v2/illust/related"
const val ILLUST_REPORT = "${ROOT_PATH}/v1/illust/report"
const val ILLUST_SERIES = "${ROOT_PATH}/v1/illust/series?filter=for_android"
const val ILLUST_SERIES_ILLUST = "${ROOT_PATH}/v1/illust-series/illust?filter=for_android"
const val MANGA_RECOMMENDED = "${ROOT_PATH}/v1/manga/recommended?filter=for_android"

// Notification
const val NOTIFICATION_NEW = "${ROOT_PATH}/v1/notification/new-from-following"
const val NOTIFICATION_SETTINGS = "${ROOT_PATH}/v1/notification/settings"
const val NOTIFICATION_SETTINGS_EDIT = "${ROOT_PATH}/v1/notification/settings/edit"
const val NOTIFICATION_USER_REGISTER = "${ROOT_PATH}/v1/notification/user/register"

// Novel
const val NOVEL_BOOKMARK_ADD =  "${ROOT_PATH}/v2/novel/bookmark/add"
const val NOVEL_BOOKMARK_DELETE = "${ROOT_PATH}/v1/novel/bookmark/delete"
const val NOVEL_BOOKMARK_DETAIL = "${ROOT_PATH}/v2/novel/bookmark/detail"
const val NOVEL_BOOKMARK_USERS = "${ROOT_PATH}/v2/novel/bookmark/users"
const val NOVEL_COMMENT_ADD = "${ROOT_PATH}/v1/novel/comment/add"
const val NOVEL_COMMENT_DELETE = "${ROOT_PATH}/v1/novel/comment/delete"
const val NOVEL_COMMENT_REPLIES = "${ROOT_PATH}/v2/novel/comment/replies"
const val NOVEL_COMMENTS = "${ROOT_PATH}/v3/novel/comments"
const val NOVEL_DETAIL = "${ROOT_PATH}/v2/novel/detail"
const val NOVEL_FOLLOW = "${ROOT_PATH}/v2/novel/follow"
const val NOVEL_MARKER_ADD = "${ROOT_PATH}/v1/novel/marker/add"
const val NOVEL_MARKER_DELETE = "${ROOT_PATH}/v1/novel/marker/delete"
const val NOVEL_MARKERS = "${ROOT_PATH}/v2/novel/markers"
const val NOVEL_MYPIXIV = "${ROOT_PATH}/v2/novel/mypixiv"
const val NOVEL_NEW = "${ROOT_PATH}/v2/novel/new"
const val NOVEL_RANKING = "${ROOT_PATH}/v1/novel/ranking"
const val NOVEL_RECOMMENDED = "${ROOT_PATH}/v1/novel/recommended"
const val NOVEL_REPORT = "${ROOT_PATH}/v1/novel/report"
const val NOVEL_SERIES = "${ROOT_PATH}/v2/novel/series"

// Search
const val SEARCH_AUTO_COMPLETE = "${ROOT_PATH}/v1/search/autocomplete"
const val SEARCH_BOOKMARK_RANGES_ILLUST = "${ROOT_PATH}/v1/search/bookmark-ranges/illust"
const val SEARCH_BOOKMARK_RANGES_NOVEL = "${ROOT_PATH}/v1/search/bookmark-ranges/novel"
const val SEARCH_ILLUST = "${ROOT_PATH}/v1/search/illust"
const val SEARCH_NOVEL = "${ROOT_PATH}/v1/search/novel?include_translated_tag_results=true&merge_plain_keyword_results=true"
const val SEARCH_POPULAR_PREVIEW_NOVEL = "${ROOT_PATH}/v1/search/popular-preview/novel?include_translated_tag_results=true&merge_plain_keyword_results=true"
const val SEARCH_POPULAR_PREVIEW_ILLUST = "${ROOT_PATH}/v1/search/popular-preview/illust?include_translated_tag_results=true&merge_plain_keyword_results=true"
const val SEARCH_USER = "${ROOT_PATH}/v1/search/user"

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
const val USER_FOLLOW_ADD = "${ROOT_PATH}/v1/user/follow/add"
const val USER_FOLLOW_DELETE = "${ROOT_PATH}/v1/user/follow/delete"
const val USER_FOLLOW_DETAIL = "${ROOT_PATH}/v1/user/follow/detail"
const val USER_FOLLOWER = "${ROOT_PATH}/v1/user/follower"
const val USER_FOLLOWING = "${ROOT_PATH}/v1/user/following"
const val USER_HISTORY_ILLUST_ADD = "${ROOT_PATH}/v2/user/browsing-history/illust/add"
const val USER_HISTORY_ILLUSTS = "${ROOT_PATH}/v1/user/browsing-history/illusts"
const val USER_HISTORY_NOVEL_ADD = "${ROOT_PATH}/v2/user/browsing-history/novel/add"
const val USER_HISTORY_NOVELS = "${ROOT_PATH}/v1/user/browsing-history/novels"
const val USER_ILLUST_SERIES = "${ROOT_PATH}/v1/user/illust-series"
const val USER_ILLUSTS = "${ROOT_PATH}/v1/user/illusts"
const val USER_ME_AUDIENCE_TARGETING = "${ROOT_PATH}/v1/user/me/audience-targeting"
const val USER_ME_STATE = "${ROOT_PATH}/v1/user/me/state"
const val USER_MYPIXIV = "${ROOT_PATH}/v1/user/mypixiv?filter=for_android"
const val USER_NOVEL_DRAFT_DELETE = "${ROOT_PATH}/v1/user/novel/draft/delete"
const val USER_NOVEL_DRAFT_DETAIL = "${ROOT_PATH}/v1/user/novel/draft/detail"
const val USER_NOVEL_DRAFT_PREVIEWS = "${ROOT_PATH}/v1/user/novel-draft-previews"
const val USER_NOVELS = "${ROOT_PATH}/v1/user/novels"
const val USER_PROFILE_EDIT = "${ROOT_PATH}/v1/user/profile/edit"
const val USER_PROFILE_PRESETS = "${ROOT_PATH}/v1/user/profile/presets"
const val USER_RECOMMENDED = "${ROOT_PATH}/v1/user/recommended"
const val USER_RELATED = "${ROOT_PATH}/v1/user/related?filter=for_android"
const val USER_REPORT = "${ROOT_PATH}/v1/user/report"
const val USER_WORKSPACE_EDIT = "${ROOT_PATH}/v1/user/workspace/edit"

// Premium
const val PREMIUM_ANDROID_PLANS = "${ROOT_PATH}/v1/premium/android/plans"
const val PREMIUM_ANDROID_LANDING_PAGE = "${ROOT_PATH}/v1/premium/android/landing-page-url"
const val PREMIUM_ANDROID_REGISTER = "${ROOT_PATH}/v1/premium/android/register"

// Walkthrough
const val WALKTHROUGH_ILLUSTS = "${ROOT_PATH}v1/walkthrough/illusts"

// Ppoint
const val PPOINT_ANDROID_ADD = "${ROOT_PATH}v1/ppoint/android/add"
const val PPOINT_ANDROID_PLANS = "${ROOT_PATH}/v1/ppoint/android/plans"
const val PPOINT_ANDROID_PRODUCTS = "${ROOT_PATH}/v1/ppoint/android/products"
const val PPOINT_CAN_PURCHASE = "${ROOT_PATH}/v1/ppoint/can-purchase"
const val PPOINT_EXPIRATIONS = "${ROOT_PATH}/v1/ppoint/expirations"
const val PPOINT_GAINS = "${ROOT_PATH}/v1/ppoint/gains"
const val PPOINT_LOSSES = "${ROOT_PATH}/v1/ppoint/losses"
const val PPOINT_SUMMARY = "${ROOT_PATH}/v1/ppoint/summary"

// Upload
const val UPLOAD_NOVEL_COVERS = "${ROOT_PATH}/v1/upload/novel/covers"
const val UPLOAD_NOVEL_DRAFT = "${ROOT_PATH}/v1/upload/novel/draft"

// Mute
const val MUTE_EDIT = "${ROOT_PATH}/v1/mute/edit"
const val MUTE_LIST = "${ROOT_PATH}/v1/mute/list"

// Other
const val APPLICATION_INFO_ANDROID = "${ROOT_PATH}/v1/application-info/android"
const val APPLICATION_INFO_IOS = "${ROOT_PATH}/v1/application-info/ios"

const val EMOJI = "${ROOT_PATH}/v1/emoji"
const val STAMPS = "${ROOT_PATH}/v1/stamps"
const val IDP_URLS = "${ROOT_PATH}/idp-urls"
// 指定されたエンドポイントは存在しません
const val FEEDBACK = "${ROOT_PATH}/v1/feedback"
const val LIVE_LIST = "${ROOT_PATH}/v1/live/list"
const val MAIL_AUTHENTICATION_SEND = "${ROOT_PATH}/v1/mail-authentication/send"
const val PRIVACY_POLICY_AGREEMENT = "${ROOT_PATH}/v1/privacy-policy/agreement"
const val EDIT_NOVEL_DRAFT = "${ROOT_PATH}/v1/edit/novel/draft"
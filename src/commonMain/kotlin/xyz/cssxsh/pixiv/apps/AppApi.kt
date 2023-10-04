package xyz.cssxsh.pixiv.apps


public const val PAGE_SIZE: Long = 30L
public const val ARTICLE_PAGE_SIZE: Long = 10L
public const val RECOMMENDED_PAGE_SIZE: Long = 90L
public const val ARTICLE_LIMIT: Long = 5000L
public const val FOLLOW_LIMIT: Long = 6000L
public const val RECOMMENDED_LIMIT: Long = 5000L
public const val RELATED_LIMIT: Long = 150L
public const val SEARCH_LIMIT: Long = 5000L
public const val TAG_LIMIT: Long = 10L
public const val BOOKMARK_TAG_LIMIT: Long = 5000L

// Illust
public const val ILLUST_BOOKMARK_ADD: String = "https://app-api.pixiv.net/v2/illust/bookmark/add"
public const val ILLUST_BOOKMARK_DELETE: String = "https://app-api.pixiv.net/v1/illust/bookmark/delete"
public const val ILLUST_BOOKMARK_DETAIL: String = "https://app-api.pixiv.net/v2/illust/bookmark/detail"
public const val ILLUST_BOOKMARK_USERS: String = "https://app-api.pixiv.net/v1/illust/bookmark/users?filter=for_android"
public const val ILLUST_COMMENT_ADD: String = "https://app-api.pixiv.net/v1/illust/comment/add"
public const val ILLUST_COMMENT_DELETE: String = "https://app-api.pixiv.net/v1/illust/comment/delete"
public const val ILLUST_COMMENT_REPLIES: String = "https://app-api.pixiv.net/v2/illust/comment/replies"
public const val ILLUST_COMMENTS: String = "https://app-api.pixiv.net/v3/illust/comments"
public const val ILLUST_DELETE: String = "https://app-api.pixiv.net/v1/illust/delete"
public const val ILLUST_DETAIL: String = "https://app-api.pixiv.net/v1/illust/detail"
public const val ILLUST_FOLLOW: String = "https://app-api.pixiv.net/v2/illust/follow"
public const val ILLUST_MYPIXIV: String = "https://app-api.pixiv.net/v2/illust/mypixiv"
public const val ILLUST_NEW: String = "https://app-api.pixiv.net/v1/illust/new"
public const val ILLUST_RANKING: String = "https://app-api.pixiv.net/v1/illust/ranking"
public const val ILLUST_RECOMMENDED: String = "https://app-api.pixiv.net/v1/illust/recommended"
public const val ILLUST_RELATED: String = "https://app-api.pixiv.net/v2/illust/related"
public const val ILLUST_REPORT: String = "https://app-api.pixiv.net/v1/illust/report"
public const val ILLUST_SERIES: String = "https://app-api.pixiv.net/v1/illust/series?filter=for_android"
public const val ILLUST_SERIES_ILLUST: String = "https://app-api.pixiv.net/v1/illust-series/illust?filter=for_android"
public const val MANGA_RECOMMENDED: String = "https://app-api.pixiv.net/v1/manga/recommended?filter=for_android"
public const val WALK_THROUGH_ILLUSTS: String = "https://app-api.pixiv.net/v1/walkthrough/illusts"

// Notification
public const val NOTIFICATION_NEW: String = "https://app-api.pixiv.net/v1/notification/new-from-following"
public const val NOTIFICATION_SETTINGS: String = "https://app-api.pixiv.net/v1/notification/settings"
public const val NOTIFICATION_SETTINGS_EDIT: String = "https://app-api.pixiv.net/v1/notification/settings/edit"
public const val NOTIFICATION_USER_REGISTER: String = "https://app-api.pixiv.net/v1/notification/user/register"

// Novel
public const val NOVEL_BOOKMARK_ADD: String = "https://app-api.pixiv.net/v2/novel/bookmark/add"
public const val NOVEL_BOOKMARK_DELETE: String = "https://app-api.pixiv.net/v1/novel/bookmark/delete"
public const val NOVEL_BOOKMARK_DETAIL: String = "https://app-api.pixiv.net/v2/novel/bookmark/detail"
public const val NOVEL_BOOKMARK_USERS: String = "https://app-api.pixiv.net/v2/novel/bookmark/users"
public const val NOVEL_COMMENT_ADD: String = "https://app-api.pixiv.net/v1/novel/comment/add"
public const val NOVEL_COMMENT_DELETE: String = "https://app-api.pixiv.net/v1/novel/comment/delete"
public const val NOVEL_COMMENT_REPLIES: String = "https://app-api.pixiv.net/v2/novel/comment/replies"
public const val NOVEL_COMMENTS: String = "https://app-api.pixiv.net/v3/novel/comments"
public const val NOVEL_DETAIL: String = "https://app-api.pixiv.net/v2/novel/detail"
public const val NOVEL_FOLLOW: String = "https://app-api.pixiv.net/v2/novel/follow"
public const val NOVEL_MARKER_ADD: String = "https://app-api.pixiv.net/v1/novel/marker/add"
public const val NOVEL_MARKER_DELETE: String = "https://app-api.pixiv.net/v1/novel/marker/delete"
public const val NOVEL_MARKERS: String = "https://app-api.pixiv.net/v2/novel/markers"
public const val NOVEL_MYPIXIV: String = "https://app-api.pixiv.net/v2/novel/mypixiv"
public const val NOVEL_NEW: String = "https://app-api.pixiv.net/v2/novel/new"
public const val NOVEL_RANKING: String = "https://app-api.pixiv.net/v1/novel/ranking"
public const val NOVEL_RECOMMENDED: String = "https://app-api.pixiv.net/v1/novel/recommended"
public const val NOVEL_REPORT: String = "https://app-api.pixiv.net/v1/novel/report"
public const val NOVEL_SERIES: String = "https://app-api.pixiv.net/v2/novel/series"

// Search
public const val SEARCH_AUTO_COMPLETE_V1: String = "https://app-api.pixiv.net/v1/search/autocomplete"
public const val SEARCH_AUTO_COMPLETE: String = "https://app-api.pixiv.net/v2/search/autocomplete"
public const val SEARCH_BOOKMARK_RANGES_ILLUST: String = "https://app-api.pixiv.net/v1/search/bookmark-ranges/illust"
public const val SEARCH_BOOKMARK_RANGES_NOVEL: String = "https://app-api.pixiv.net/v1/search/bookmark-ranges/novel"
public const val SEARCH_ILLUST: String = "https://app-api.pixiv.net/v1/search/illust"
// TODO: include_translated_tag_results=true&merge_plain_keyword_results=true
public const val SEARCH_NOVEL: String = "https://app-api.pixiv.net/v1/search/novel"
public const val SEARCH_POPULAR_PREVIEW_ILLUST: String = "https://app-api.pixiv.net/v1/search/popular-preview/illust"
public const val SEARCH_POPULAR_PREVIEW_NOVEL: String = "https://app-api.pixiv.net/v1/search/popular-preview/novel"
public const val SEARCH_USER: String = "https://app-api.pixiv.net/v1/search/user"

// Spotlight
public const val SPOTLIGHT_ARTICLES: String = "https://app-api.pixiv.net/v1/spotlight/articles"

// Trending
public const val TRENDING_TAGS_ILLUST: String = "https://app-api.pixiv.net/v1/trending-tags/illust"
public const val TRENDING_TAGS_NOVEL: String = "https://app-api.pixiv.net/v1/trending-tags/novel"

// Ugoira
public const val UGOIRA_METADATA: String = "https://app-api.pixiv.net/v1/ugoira/metadata"

// User
public const val USER_BLACKLIST: String = "https://app-api.pixiv.net/v2/user/list"
public const val USER_BOOKMARKS_ILLUST: String = "https://app-api.pixiv.net/v1/user/bookmarks/illust"
public const val USER_BOOKMARKS_NOVEL: String = "https://app-api.pixiv.net/v1/user/bookmarks/novel"
public const val USER_BOOKMARKS_TAGS_ILLUST: String = "https://app-api.pixiv.net/v1/user/bookmark-tags/illust"
public const val USER_BOOKMARKS_TAGS_NOVEL: String = "https://app-api.pixiv.net/v1/user/bookmark-tags/novel"
public const val USER_DETAIL: String = "https://app-api.pixiv.net/v1/user/detail"
public const val USER_FOLLOW_ADD: String = "https://app-api.pixiv.net/v1/user/follow/add"
public const val USER_FOLLOW_DELETE: String = "https://app-api.pixiv.net/v1/user/follow/delete"
public const val USER_FOLLOW_DETAIL: String = "https://app-api.pixiv.net/v1/user/follow/detail"
public const val USER_FOLLOWER: String = "https://app-api.pixiv.net/v1/user/follower"
public const val USER_FOLLOWING: String = "https://app-api.pixiv.net/v1/user/following"
public const val USER_HISTORY_ILLUST_ADD: String = "https://app-api.pixiv.net/v2/user/browsing-history/illust/add"
public const val USER_HISTORY_ILLUSTS: String = "https://app-api.pixiv.net/v1/user/browsing-history/illusts"
public const val USER_HISTORY_NOVEL_ADD: String = "https://app-api.pixiv.net/v2/user/browsing-history/novel/add"
public const val USER_HISTORY_NOVELS: String = "https://app-api.pixiv.net/v1/user/browsing-history/novels"
public const val USER_ILLUST_SERIES: String = "https://app-api.pixiv.net/v1/user/illust-series"
public const val USER_ILLUSTS: String = "https://app-api.pixiv.net/v1/user/illusts"
public const val USER_ME_AUDIENCE_TARGETING: String = "https://app-api.pixiv.net/v1/user/me/audience-targeting"
public const val USER_ME_STATE: String = "https://app-api.pixiv.net/v1/user/me/state"
public const val USER_MYPIXIV: String = "https://app-api.pixiv.net/v1/user/mypixiv"
public const val USER_NOVEL_DRAFT_DELETE: String = "https://app-api.pixiv.net/v1/user/novel/draft/delete"
public const val USER_NOVEL_DRAFT_DETAIL: String = "https://app-api.pixiv.net/v1/user/novel/draft/detail"
public const val USER_NOVEL_DRAFT_PREVIEWS: String = "https://app-api.pixiv.net/v1/user/novel-draft-previews"
public const val USER_NOVELS: String = "https://app-api.pixiv.net/v1/user/novels"
public const val USER_PROFILE_EDIT: String = "https://app-api.pixiv.net/v1/user/profile/edit"
public const val USER_PROFILE_PRESETS: String = "https://app-api.pixiv.net/v1/user/profile/presets"
public const val USER_RECOMMENDED: String = "https://app-api.pixiv.net/v1/user/recommended"
public const val USER_RELATED: String = "https://app-api.pixiv.net/v1/user/related?filter=for_android"
public const val USER_REPORT: String = "https://app-api.pixiv.net/v1/user/report"
public const val USER_WORKSPACE_EDIT: String = "https://app-api.pixiv.net/v1/user/workspace/edit"

// Premium
public const val PREMIUM_ANDROID_PLANS: String = "https://app-api.pixiv.net/v1/premium/android/plans"
public const val PREMIUM_ANDROID_LANDING_PAGE: String = "https://app-api.pixiv.net/v1/premium/android/landing-page-url"
public const val PREMIUM_ANDROID_REGISTER: String = "https://app-api.pixiv.net/v1/premium/android/register"

// Ppoint
public const val PPOINT_ANDROID_ADD: String = "https://app-api.pixiv.netv1/ppoint/android/add"
public const val PPOINT_ANDROID_PLANS: String = "https://app-api.pixiv.net/v1/ppoint/android/plans"
public const val PPOINT_ANDROID_PRODUCTS: String = "https://app-api.pixiv.net/v1/ppoint/android/products"
public const val PPOINT_CAN_PURCHASE: String = "https://app-api.pixiv.net/v1/ppoint/can-purchase"
public const val PPOINT_EXPIRATIONS: String = "https://app-api.pixiv.net/v1/ppoint/expirations"
public const val PPOINT_GAINS: String = "https://app-api.pixiv.net/v1/ppoint/gains"
public const val PPOINT_LOSSES: String = "https://app-api.pixiv.net/v1/ppoint/losses"
public const val PPOINT_SUMMARY: String = "https://app-api.pixiv.net/v1/ppoint/summary"

// Upload
public const val UPLOAD_NOVEL_COVERS: String = "https://app-api.pixiv.net/v1/upload/novel/covers"
public const val UPLOAD_NOVEL_DRAFT: String = "https://app-api.pixiv.net/v1/upload/novel/draft"

// Mute
public const val MUTE_EDIT: String = "https://app-api.pixiv.net/v1/mute/edit"
public const val MUTE_LIST: String = "https://app-api.pixiv.net/v1/mute/list"

// Other
public const val APPLICATION_INFO_ANDROID: String = "https://app-api.pixiv.net/v1/application-info/android"
public const val APPLICATION_INFO_IOS: String = "https://app-api.pixiv.net/v1/application-info/ios"

public const val EMOJI: String = "https://app-api.pixiv.net/v1/emoji"
public const val STAMPS: String = "https://app-api.pixiv.net/v1/stamps"
public const val IDP_URLS: String = "https://app-api.pixiv.net/idp-urls"

// 指定されたエンドポイントは存在しません
// public const val FEEDBACK: String = "https://app-api.pixiv.net/v1/feedback"
public const val LIVE_LIST: String = "https://app-api.pixiv.net/v1/live/list"
public const val MAIL_AUTHENTICATION_SEND: String = "https://app-api.pixiv.net/v1/mail-authentication/send"
public const val PRIVACY_POLICY_AGREEMENT: String = "https://app-api.pixiv.net/v1/privacy-policy/agreement"
public const val EDIT_NOVEL_DRAFT: String = "https://app-api.pixiv.net/v1/edit/novel/draft"
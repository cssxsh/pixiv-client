package xyz.cssxsh.pixiv.api.app

import com.soywiz.klock.wrapped.WDate
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonElementSerializer
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.Method
import xyz.cssxsh.pixiv.PublicityType
import xyz.cssxsh.pixiv.ContentType
import xyz.cssxsh.pixiv.RankMode
import xyz.cssxsh.pixiv.data.app.BookmarkDetailSingle
import xyz.cssxsh.pixiv.data.app.CommentData
import xyz.cssxsh.pixiv.data.app.IllustData
import xyz.cssxsh.pixiv.data.app.IllustSingle
import xyz.cssxsh.pixiv.data.app.RecommendedData

suspend fun PixivClient.illustBookmarkAdd(
    pid: Long,
    tags: Iterable<String>,
    restrict: PublicityType = PublicityType.PUBLIC
): JsonElement = useRESTful(
    method = Method.POST,
    apiUrl = AppApiUrls.illustBookmarkAdd,
    deserializer = JsonElementSerializer,
    paramsMap = mapOf(
        "illust_id" to pid,
        "restrict" to restrict,
        "tags" to tags.joinToString(separator = " ", postfix = " ")
    )
)

suspend fun PixivClient.illustBookmarkDelete(
    pid: Long
): JsonElement = useRESTful(
    method = Method.POST,
    apiUrl = AppApiUrls.illustBookmarkDelete,
    deserializer = JsonElementSerializer,
    paramsMap = mapOf(
        "illust_id" to pid
    )
)

suspend fun PixivClient.illustBookmarkDetail(
    pid: Long
): BookmarkDetailSingle = useRESTful(
    method = Method.GET,
    apiUrl = AppApiUrls.illustBookmarkDetail,
    deserializer = BookmarkDetailSingle.serializer(),
    paramsMap = mapOf(
        "illust_id" to pid
    )
)

suspend fun PixivClient.illustComments(
    pid: Long,
    offset: Long = 0,
    includeTotalComments: Boolean? = null
): CommentData = useRESTful(
    method = Method.GET,
    apiUrl = AppApiUrls.illustComments,
    deserializer = CommentData.serializer(),
    paramsMap = mapOf(
        "illust_id" to pid,
        "offset" to offset,
        "include_total_comments" to includeTotalComments
    )
)

suspend fun PixivClient.illustDetail(
    pid: Long
): IllustSingle = useRESTful(
    method = Method.GET,
    apiUrl = AppApiUrls.illustDetail,
    deserializer = IllustSingle.serializer(),
    paramsMap = mapOf(
        "illust_id" to pid
    )
)

suspend fun PixivClient.illustFollow(
    restrict: PublicityType = PublicityType.PUBLIC,
    offset: Long = 0
): IllustData = useRESTful(
    method = Method.GET,
    apiUrl = AppApiUrls.illustFollow,
    deserializer = IllustData.serializer(),
    paramsMap = mapOf(
        "restrict" to restrict,
        "offset" to offset
    )
)

suspend fun PixivClient.illustMyPixiv(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = useRESTful(
    method = Method.GET,
    apiUrl = AppApiUrls.illustMyPixiv,
    deserializer = IllustData.serializer(),
    paramsMap = mapOf(
        "content_type" to contentType,
        "restrict" to restrict,
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.illustNew(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = useRESTful(
    method = Method.GET,
    apiUrl = AppApiUrls.illustNew,
    deserializer = IllustData.serializer(),
    paramsMap = mapOf(
        "content_type" to contentType,
        "restrict" to restrict,
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.illustRanking(
    date: String? = null,
    mode: RankMode? = null,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = useRESTful(
    method = Method.GET,
    apiUrl = AppApiUrls.illustRanking,
    deserializer = IllustData.serializer(),
    paramsMap = mapOf(
        "date" to date,
        "mode" to mode,
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.illustRanking(
    date: WDate,
    mode: RankMode? = null,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = illustRanking(
    date = date.format(format = "yyyy-MM-dd"),
    mode = mode,
    filter = filter,
    offset = offset
)

suspend fun PixivClient.illustRecommended(
    contentType: ContentType = ContentType.ILLUST,
    filter: String = "for_ios",
    includeRankingLabel: Boolean = true,
    includePrivacyPolicy: Boolean = true,
    minBookmarkIdForRecentIllust: Long? = null,
    maxBookmarkIdForRecommend: Long? = null
): RecommendedData = useRESTful(
    method = Method.GET,
    apiUrl = AppApiUrls.illustRecommended,
    deserializer = RecommendedData.serializer(),
    paramsMap = mapOf(
        "content_type" to contentType,
        "filter" to filter,
        "include_ranking_label" to includeRankingLabel,
        "include_privacy_policy" to includePrivacyPolicy,
        "min_bookmark_id_for_recent_illust" to minBookmarkIdForRecentIllust,
        "max_bookmark_id_for_recommend" to maxBookmarkIdForRecommend,
    )
)

suspend fun PixivClient.illustRelated(
    pid: Long,
    seedIllustIds: Iterable<Long> = listOf(),
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = useRESTful(
    method = Method.GET,
    apiUrl = AppApiUrls.illustRelated,
    deserializer = IllustData.serializer(),
    paramsMap = mapOf(
        "illust_id" to pid,
        "filter" to filter,
        "offset" to offset,
        "seed_illust_ids" to seedIllustIds
    )
)
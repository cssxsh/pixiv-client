package xyz.cssxsh.pixiv.api.app

import com.soywiz.klock.wrapped.WDate
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.ContentType
import xyz.cssxsh.pixiv.data.app.*

suspend fun PixivClient.illustBookmarkAdd(
    pid: Long,
    tags: List<String>,
    restrict: PublicityType = PublicityType.PUBLIC
): JsonElement = httpClient.post(AppApiUrls.illustBookmarkAdd) {
    body = FormDataContent(Parameters.build {
        append("illust_id", pid.toString())
        append("tags", tags.joinToString(separator = " ", postfix = " "))
        append("restrict", restrict.value())
    })
}

suspend fun PixivClient.illustBookmarkDelete(
    pid: Long
): JsonElement = httpClient.post(AppApiUrls.illustBookmarkDelete) {
    body = FormDataContent(Parameters.build {
        parameter("illust_id", pid)
    })
}

suspend fun PixivClient.illustBookmarkDetail(
    pid: Long
): BookmarkDetailSingle = httpClient.get(AppApiUrls.illustBookmarkDetail) {
    parameter("illust_id", pid)
}

suspend fun PixivClient.illustComments(
    pid: Long,
    offset: Long = 0,
    includeTotalComments: Boolean? = null
): CommentData = httpClient.get(AppApiUrls.illustComments) {
    parameter("illust_id", pid.toString())
    parameter("offset", offset)
    parameter("include_total_comments", includeTotalComments)
}

suspend fun PixivClient.illustDetail(
    pid: Long
): IllustSingle = httpClient.get(AppApiUrls.illustDetail) {
    parameter("illust_id", pid.toString())
}

suspend fun PixivClient.illustFollow(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    offset: Long = 0
): IllustData = httpClient.get(AppApiUrls.illustFollow) {
    parameter("content_type", contentType.value())
    parameter("restrict", restrict.value())
    parameter("offset", offset)
}

suspend fun PixivClient.illustMyPixiv(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = httpClient.get(AppApiUrls.illustMyPixiv) {
    parameter("content_type", contentType.value())
    parameter("restrict", restrict.value())
    parameter("filter", filter)
    parameter("offset", offset)
}

suspend fun PixivClient.illustNew(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = httpClient.get(AppApiUrls.illustNew) {
    parameter("content_type", contentType.value())
    parameter("restrict", restrict.value())
    parameter("filter", filter)
    parameter("offset", offset)
}

suspend fun PixivClient.illustRanking(
    date: String? = null,
    mode: RankMode? = null,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = httpClient.get(AppApiUrls.illustRanking) {
    parameter("date", date)
    parameter("mode", mode?.value())
    parameter("filter", filter)
    parameter("offset", offset)
}

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
): RecommendedData = httpClient.get(AppApiUrls.illustRecommended) {
    parameter("content_type", contentType.value())
    parameter("filter", filter)
    parameter("include_ranking_label", includeRankingLabel)
    parameter("include_privacy_policy", includePrivacyPolicy)
    parameter("min_bookmark_id_for_recent_illust", minBookmarkIdForRecentIllust)
    parameter("max_bookmark_id_for_recommend", maxBookmarkIdForRecommend)
}

suspend fun PixivClient.illustRelated(
    pid: Long,
    seedIllustIds: List<Long> = listOf(),
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = httpClient.get(AppApiUrls.illustRelated) {
    parameter("illust_id", pid)
    parameter("filter", filter)
    parameter("offset", offset)
    seedIllustIds.forEachIndexed { index, item ->
        parameter("seed_illust_ids[$index]", item)
    }
}
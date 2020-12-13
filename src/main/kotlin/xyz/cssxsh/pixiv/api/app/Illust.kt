package xyz.cssxsh.pixiv.api.app

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.WorkContentType
import xyz.cssxsh.pixiv.data.app.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

suspend fun PixivClient.illustBookmarkAdd(
    pid: Long,
    tags: List<String>,
    restrict: PublicityType = PublicityType.PUBLIC,
    url: String = AppApi.ILLUST_BOOKMARK_ADD,
    ignore: (Throwable) -> Boolean = { _ -> false },
): JsonElement = useHttpClient(ignore) { client ->
    client.post(url) {
        body = FormDataContent(Parameters.build {
            append("illust_id", pid.toString())
            append("tags", tags.joinToString(separator = " ", postfix = " "))
            append("restrict", restrict.value())
        })
    }
}

suspend fun PixivClient.illustBookmarkDelete(
    pid: Long,
    url: String = AppApi.ILLUST_BOOKMARK_DELETE,
    ignore: (Throwable) -> Boolean = { _ -> false },
): JsonElement = useHttpClient(ignore) { client ->
    client.post(url) {
        body = FormDataContent(Parameters.build {
            append("illust_id", pid.toString())
        })
    }
}

suspend fun PixivClient.illustBookmarkDetail(
    pid: Long,
    url: String = AppApi.ILLUST_BOOKMARK_DETAIL,
    ignore: (Throwable) -> Boolean = { _ -> false },
): BookmarkDetailSingle = useHttpClient(ignore) { client ->
    client.get(url) {
        parameter("illust_id", pid)
    }
}

suspend fun PixivClient.illustComments(
    pid: Long,
    offset: Long = 0,
    includeTotalComments: Boolean? = null,
    url: String = AppApi.ILLUST_COMMENTS,
    ignore: (Throwable) -> Boolean = { _ -> false },
): CommentData = useHttpClient(ignore) { client ->
    client.get(url) {
        parameter("illust_id", pid.toString())
        parameter("offset", offset)
        parameter("include_total_comments", includeTotalComments)
    }
}

suspend fun PixivClient.illustDetail(
    pid: Long,
    url: String = AppApi.ILLUST_DETAIL,
    ignore: (Throwable) -> Boolean = { _ -> false },
): IllustSingle = useHttpClient(ignore) { client ->
    client.get(url) {
        parameter("illust_id", pid.toString())
    }
}

suspend fun PixivClient.illustFollow(
    workContentType: WorkContentType = WorkContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    offset: Long = 0,
    url: String = AppApi.ILLUST_FOLLOW,
    ignore: (Throwable) -> Boolean = { _ -> false },
): IllustData = useHttpClient(ignore) { client ->
    client.get(url) {
        parameter("content_type", workContentType.value())
        parameter("restrict", restrict.value())
        parameter("offset", offset)
    }
}

suspend fun PixivClient.illustMyPixiv(
    workContentType: WorkContentType = WorkContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = AppApi.ILLUST_MYPIXIV,
    ignore: (Throwable) -> Boolean = { _ -> false },
): IllustData = useHttpClient(ignore) { client ->
    client.get(url) {
        parameter("content_type", workContentType.value())
        parameter("restrict", restrict.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.illustNew(
    workContentType: WorkContentType = WorkContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = AppApi.ILLUST_NEW,
    ignore: (Throwable) -> Boolean = { _ -> false },
): IllustData = useHttpClient(ignore) { client ->
    client.get(url) {
        parameter("content_type", workContentType.value())
        parameter("restrict", restrict.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.illustRanking(
    date: String? = null,
    mode: RankMode? = null,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = AppApi.ILLUST_RANKING,
    ignore: (Throwable) -> Boolean = { _ -> false },
): IllustData = useHttpClient(ignore) { client ->
    client.get(url) {
        parameter("date", date)
        parameter("mode", mode?.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.illustRanking(
    date: LocalDate,
    mode: RankMode? = null,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = AppApi.ILLUST_RANKING,
    ignore: (Throwable) -> Boolean = { _ -> false },
): IllustData = illustRanking(
    date = date.format(DateTimeFormatter.ISO_DATE),
    mode = mode,
    filter = filter,
    offset = offset,
    url = url,
    ignore = ignore
)

suspend fun PixivClient.illustRecommended(
    workContentType: WorkContentType = WorkContentType.ILLUST,
    filter: String = "for_ios",
    includeRankingLabel: Boolean = true,
    includePrivacyPolicy: Boolean = true,
    minBookmarkIdForRecentIllust: Long? = null,
    maxBookmarkIdForRecommend: Long? = null,
    url: String = AppApi.ILLUST_RECOMMENDED,
    ignore: (Throwable) -> Boolean = { _ -> false },
): RecommendedData = useHttpClient(ignore) { client ->
    client.get(url) {
        parameter("content_type", workContentType.value())
        parameter("filter", filter)
        parameter("include_ranking_label", includeRankingLabel)
        parameter("include_privacy_policy", includePrivacyPolicy)
        parameter("min_bookmark_id_for_recent_illust", minBookmarkIdForRecentIllust)
        parameter("max_bookmark_id_for_recommend", maxBookmarkIdForRecommend)
    }
}

suspend fun PixivClient.illustRelated(
    pid: Long,
    seedIllustIds: List<Long> = emptyList(),
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = AppApi.ILLUST_RELATED,
    ignore: (Throwable) -> Boolean = { _ -> false },
): IllustData = useHttpClient(ignore) { client ->
    client.get(url) {
        parameter("illust_id", pid)
        parameter("filter", filter)
        parameter("offset", offset)
        seedIllustIds.forEachIndexed { index, item ->
            parameter("seed_illust_ids[$index]", item)
        }
    }
}

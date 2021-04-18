package xyz.cssxsh.pixiv.api.apps

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.WorkContentType
import xyz.cssxsh.pixiv.data.apps.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

suspend fun PixivClient.illustBookmarkAdd(
    pid: Long,
    tags: List<String>,
    restrict: PublicityType = PublicityType.PUBLIC,
    url: String = ILLUST_BOOKMARK_ADD,
): JsonElement = useHttpClient { client ->
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
    url: String = ILLUST_BOOKMARK_DELETE,
): JsonElement = useHttpClient { client ->
    client.post(url) {
        body = FormDataContent(Parameters.build {
            append("illust_id", pid.toString())
        })
    }
}

suspend fun PixivClient.illustBookmarkDetail(
    pid: Long,
    url: String = ILLUST_BOOKMARK_DETAIL,
): BookmarkDetailSingle = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid)
    }
}

suspend fun PixivClient.illustComments(
    pid: Long,
    offset: Long = 0,
    includeTotalComments: Boolean? = null,
    url: String = ILLUST_COMMENTS,
): CommentData = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid.toString())
        parameter("offset", offset)
        parameter("include_total_comments", includeTotalComments)
    }
}

suspend fun PixivClient.illustDetail(
    pid: Long,
    url: String = ILLUST_DETAIL,
): IllustSingle = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid.toString())
    }
}

suspend fun PixivClient.illustFollow(
    type: WorkContentType = WorkContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    offset: Long = 0,
    url: String = ILLUST_FOLLOW,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("content_type", type.value())
        parameter("restrict", restrict.value())
        parameter("offset", offset)
    }
}

suspend fun PixivClient.illustMyPixiv(
    type: WorkContentType = WorkContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = ILLUST_MYPIXIV,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("content_type", type.value())
        parameter("restrict", restrict.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.illustNew(
    type: WorkContentType = WorkContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = ILLUST_NEW,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("content_type", type.value())
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
    url: String = ILLUST_RANKING,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("date", date)
        parameter("mode", mode?.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.illustRanking(
    date: LocalDate?,
    mode: RankMode? = null,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = ILLUST_RANKING,
): IllustData = illustRanking(
    date = date?.format(DateTimeFormatter.ISO_DATE),
    mode = mode,
    filter = filter,
    offset = offset,
    url = url,
)

suspend fun PixivClient.illustRecommended(
    type: WorkContentType = WorkContentType.ILLUST,
    filter: String = "for_ios",
    includeRankingLabel: Boolean = true,
    includePrivacyPolicy: Boolean = true,
    minBookmarkIdForRecentIllust: Long? = null,
    maxBookmarkIdForRecommend: Long? = null,
    url: String = ILLUST_RECOMMENDED,
): RecommendedData = useHttpClient { client ->
    client.get(url) {
        parameter("content_type", type.value())
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
    url: String = ILLUST_RELATED,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid)
        parameter("filter", filter)
        parameter("offset", offset)
        seedIllustIds.forEachIndexed { index, item ->
            parameter("seed_illust_ids[$index]", item)
        }
    }
}

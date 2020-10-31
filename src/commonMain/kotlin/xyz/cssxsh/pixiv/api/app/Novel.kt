package xyz.cssxsh.pixiv.api.app

import com.soywiz.klock.wrapped.WDate
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.PublicityType
import xyz.cssxsh.pixiv.RankMode
import xyz.cssxsh.pixiv.WorkContentType
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.*
import xyz.cssxsh.pixiv.useHttpClient

suspend fun PixivClient.novelBookmarkAdd(
    pid: Long,
    tags: List<String>,
    restrict: PublicityType = PublicityType.PUBLIC,
    url: String = AppApi.NOVEL_BOOKMARK_ADD
): JsonElement = useHttpClient { client ->
    client.post(url) {
        body = FormDataContent(Parameters.build {
            append("novel_id", pid.toString())
            append("tags", tags.joinToString(separator = " ", postfix = " "))
            append("restrict", restrict.value())
        })
    }
}

suspend fun PixivClient.novelBookmarkDelete(
    pid: Long,
    url: String = AppApi.NOVEL_BOOKMARK_DELETE
): JsonElement = useHttpClient { client ->
    client.post(url) {
        body = FormDataContent(Parameters.build {
            append("novel_id", pid.toString())
        })
    }
}

suspend fun PixivClient.novelBookmarkDetail(
    pid: Long,
    url: String = AppApi.NOVEL_BOOKMARK_DETAIL
): BookmarkDetailSingle = useHttpClient { client ->
    client.get(url) {
        parameter("novel_id", pid)
    }
}


suspend fun PixivClient.novelComments(
    pid: Long,
    offset: Long = 0,
    includeTotalComments: Boolean? = null,
    url: String = AppApi.NOVEL_COMMENTS
): CommentData = useHttpClient { client ->
    client.get(url) {
        parameter("novel_id", pid.toString())
        parameter("offset", offset)
        parameter("include_total_comments", includeTotalComments)
    }
}

suspend fun PixivClient.novelDetail(
    pid: Long,
    url: String = AppApi.NOVEL_DETAIL
): IllustSingle = useHttpClient { client ->
    client.get(url) {
        parameter("novel_id", pid.toString())
    }
}

suspend fun PixivClient.novelFollow(
    workContentType: WorkContentType = WorkContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    offset: Long = 0,
    url: String = AppApi.NOVEL_FOLLOW
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("content_type", workContentType.value())
        parameter("restrict", restrict.value())
        parameter("offset", offset)
    }
}

suspend fun PixivClient.novelMyPixiv(
    workContentType: WorkContentType = WorkContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = AppApi.NOVEL_MYPIXIV
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("content_type", workContentType.value())
        parameter("restrict", restrict.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.novelNew(
    workContentType: WorkContentType = WorkContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = AppApi.NOVEL_NEW
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("content_type", workContentType.value())
        parameter("restrict", restrict.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.novelRanking(
    date: String? = null,
    mode: RankMode? = null,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = AppApi.NOVEL_RANKING
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("date", date)
        parameter("mode", mode?.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.novelRanking(
    date: WDate,
    mode: RankMode? = null,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = AppApi.NOVEL_RANKING
): IllustData = illustRanking(
    date = date.format(format = "yyyy-MM-dd"),
    mode = mode,
    filter = filter,
    offset = offset,
    url = url
)

suspend fun PixivClient.novelRecommended(
    workContentType: WorkContentType = WorkContentType.ILLUST,
    filter: String = "for_ios",
    includeRankingLabel: Boolean = true,
    includePrivacyPolicy: Boolean = true,
    minBookmarkIdForRecentIllust: Long? = null,
    maxBookmarkIdForRecommend: Long? = null,
    url: String = AppApi.NOVEL_RECOMMENDED
): RecommendedData = useHttpClient { client ->
    client.get(url) {
        parameter("content_type", workContentType.value())
        parameter("filter", filter)
        parameter("include_ranking_label", includeRankingLabel)
        parameter("include_privacy_policy", includePrivacyPolicy)
        parameter("min_bookmark_id_for_recent_illust", minBookmarkIdForRecentIllust)
        parameter("max_bookmark_id_for_recommend", maxBookmarkIdForRecommend)
    }
}
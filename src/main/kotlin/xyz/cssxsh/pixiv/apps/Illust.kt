package xyz.cssxsh.pixiv.apps

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.*

public suspend fun PixivAppClient.illustBookmarkAdd(
    pid: Long,
    tags: Set<String>,
    restrict: PublicityType = PublicityType.PUBLIC,
    url: String = ILLUST_BOOKMARK_ADD,
): JsonElement = useHttpClient { client ->
    client.submitForm(url, Parameters.build {
        append("illust_id", pid.toString())
        for ((index, item) in tags.withIndex()) {
            append("tags[$index]", item)
        }
        append("restrict", restrict.toString())
    }).body()
}

public suspend fun PixivAppClient.illustBookmarkDelete(
    pid: Long,
    url: String = ILLUST_BOOKMARK_DELETE,
): JsonElement = useHttpClient { client ->
    client.submitForm(url, Parameters.build {
        append("illust_id", pid.toString())
    }).body()
}

public suspend fun PixivAppClient.illustBookmarkDetail(
    pid: Long,
    url: String = ILLUST_BOOKMARK_DETAIL,
): BookmarkDetailSingle = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid)
    }.body()
}

public suspend fun PixivAppClient.illustComments(
    pid: Long,
    offset: Long? = null,
    includeTotalComments: Boolean? = null,
    url: String = ILLUST_COMMENTS,
): CommentData = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid.toString())
        parameter("offset", offset)
        parameter("include_total_comments", includeTotalComments)
    }.body()
}

public suspend fun PixivAppClient.illustDetail(
    pid: Long,
    url: String = ILLUST_DETAIL,
): IllustSingle = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid.toString())
    }.body()
}

public suspend fun PixivAppClient.illustFollow(
    restrict: PublicityType = PublicityType.PUBLIC,
    offset: Long? = null,
    url: String = ILLUST_FOLLOW,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("restrict", restrict)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.illustMyPixiv(
    offset: Long? = null,
    url: String = ILLUST_MYPIXIV,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.illustNew(
    type: WorkContentType? = null,
    max: Long? = null,
    url: String = ILLUST_NEW,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("content_type", type)
        parameter("max_illust_id", max)
    }.body()
}

public suspend fun PixivAppClient.illustRanking(
    date: LocalDate? = null,
    mode: RankMode? = null,
    filter: FilterType? = null,
    offset: Long? = null,
    url: String = ILLUST_RANKING,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("date", date?.toString())
        parameter("mode", mode)
        parameter("filter", filter)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.illustRecommended(
    filter: FilterType? = null,
    includeRankingLabel: Boolean? = null,
    includePrivacyPolicy: Boolean? = null,
    minBookmarkIdForRecentIllust: Long? = null,
    maxBookmarkIdForRecommend: Long? = null,
    offset: Long? = null,
    url: String = ILLUST_RECOMMENDED,
): RecommendedData = useHttpClient { client ->
    client.get(url) {
        parameter("filter", filter)
        parameter("include_ranking_label", includeRankingLabel)
        parameter("include_privacy_policy", includePrivacyPolicy)
        parameter("min_bookmark_id_for_recent_illust", minBookmarkIdForRecentIllust)
        parameter("max_bookmark_id_for_recommend", maxBookmarkIdForRecommend)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.illustRelated(
    pid: Long,
    filter: FilterType? = null,
    offset: Long? = null,
    url: String = ILLUST_RELATED,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid)
        parameter("filter", filter)
        parameter("offset", offset)
    }.body()
}

public suspend fun PixivAppClient.illustWalkThrough(
    url: String = WALK_THROUGH_ILLUSTS,
): IllustData = useHttpClient { client ->
    client.get(url).body()
}

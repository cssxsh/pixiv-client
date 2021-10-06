package xyz.cssxsh.pixiv.apps

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*
import java.time.*
import java.time.format.*

suspend fun PixivAppClient.searchAutoCompleteOld(
    word: String,
    url: String = SEARCH_AUTO_COMPLETE_V1,
): KeywordsData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
    }
}

suspend fun PixivAppClient.searchAutoComplete(
    word: String,
    merge: Boolean? = null,
    url: String = SEARCH_AUTO_COMPLETE,
): TagData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
        parameter("merge_plain_keyword_results", merge)
    }
}

suspend fun PixivAppClient.searchBookmarkRangesIllust(
    word: String,
    target: SearchTarget? = null,
    start: LocalDate? = null,
    end: LocalDate? = null,
    translated: Boolean? = null,
    merge: Boolean? = null,
    filter: FilterType? = null,
    offset: Long? = null,
    url: String = SEARCH_BOOKMARK_RANGES_ILLUST,
): BookmarkRangeData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
        parameter("search_target", target)
        parameter("start_date", start?.format(DateTimeFormatter.ISO_DATE))
        parameter("end_date", end?.format(DateTimeFormatter.ISO_DATE))
        parameter("include_translated_tag_results", translated)
        parameter("merge_plain_keyword_results", merge)
        parameter("offset", offset)
        parameter("filter", filter)
    }
}

/**
 * url or [SEARCH_POPULAR_PREVIEW_ILLUST]
 */
suspend fun PixivAppClient.searchIllust(
    word: String,
    target: SearchTarget? = null,
    sort: SearchSort? = null,
    duration: SearchDuration? = null,
    min: Long? = null,
    max: Long? = null,
    start: LocalDate? = null,
    end: LocalDate? = null,
    translated: Boolean? = null,
    merge: Boolean? = null,
    offset: Long? = null,
    filter: FilterType? = null,
    url: String = SEARCH_ILLUST,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
        parameter("search_target", target)
        parameter("sort", sort ?: if (info().user.isPremium) SearchSort.POPULAR_DESC else SearchSort.DATE_DESC)
        parameter("duration", duration)
        parameter("bookmark_num_min", min)
        parameter("bookmark_num_max", max)
        parameter("start_date", start?.format(DateTimeFormatter.ISO_DATE))
        parameter("end_date", end?.format(DateTimeFormatter.ISO_DATE))
        parameter("include_translated_tag_results", translated)
        parameter("merge_plain_keyword_results", merge)
        parameter("offset", offset)
        parameter("filter", filter)
    }
}

suspend fun PixivAppClient.searchUser(
    word: String,
    filter: FilterType? = null,
    offset: Long? = null,
    url: String = SEARCH_USER,
): PreviewData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

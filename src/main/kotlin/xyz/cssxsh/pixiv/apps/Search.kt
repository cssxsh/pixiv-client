package xyz.cssxsh.pixiv.apps

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

suspend fun PixivClient.searchAutoComplete(
    word: String,
    url: String = SEARCH_AUTO_COMPLETE,
): KeywordsData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
    }
}

suspend fun PixivClient.searchBookmarkRangesIllust(
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
        parameter("search_target", target?.value())
        parameter("start_date", start?.format(DateTimeFormatter.ISO_DATE))
        parameter("end_date", end?.format(DateTimeFormatter.ISO_DATE))
        parameter("include_translated_tag_results", translated)
        parameter("merge_plain_keyword_results", merge)
        parameter("offset", offset)
        parameter("filter", filter?.value())
    }
}

suspend fun PixivClient.searchIllust(
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
        parameter("search_target", target?.value())
        parameter("sort", sort?.value())
        parameter("duration", duration?.value())
        parameter("bookmark_num_min", min)
        parameter("bookmark_num_max", max)
        parameter("start_date", start?.format(DateTimeFormatter.ISO_DATE))
        parameter("end_date", end?.format(DateTimeFormatter.ISO_DATE))
        parameter("include_translated_tag_results", translated)
        parameter("merge_plain_keyword_results", merge)
        parameter("offset", offset)
        parameter("filter", filter?.value())
    }
}

suspend fun PixivClient.searchUser(
    word: String,
    filter: FilterType? = null,
    offset: Long? = null,
    url: String = SEARCH_USER,
): PreviewData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
        parameter("filter", filter?.value())
        parameter("offset", offset)
    }
}

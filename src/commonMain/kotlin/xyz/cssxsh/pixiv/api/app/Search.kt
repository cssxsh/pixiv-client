package xyz.cssxsh.pixiv.api.app

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.*

suspend fun PixivClient.searchIllust(
    word: String,
    searchTarget: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    sort: SortType = SortType.DATE_DESC,
    duration: DurationType? = null,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = httpClient.get(AppApiUrls.searchIllust) {
    parameter("word", word)
    parameter("search_target", searchTarget.value())
    parameter("sort", sort.value())
    duration?.let { parameter("duration", it.value()) }
    parameter("filter", filter)
    parameter("offset", offset)
}

suspend fun PixivClient.searchNovel(
    word: String,
    searchTarget: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    sort: SortType = SortType.DATE_DESC,
    duration: DurationType? = null,
    filter: String = "for_ios",
    offset: Long = 0
): NovelData = httpClient.get(AppApiUrls.searchNovel) {
    parameter("word", word)
    parameter("search_target", searchTarget.value())
    parameter("sort", sort.value())
    duration?.let { parameter("duration", it.value()) }
    parameter("filter", filter)
    parameter("offset", offset)
}

suspend fun PixivClient.searchAutoComplete(
    word: String
): KeywordsData = httpClient.get(AppApiUrls.searchAutoComplete) {
    parameter("word", word)
}
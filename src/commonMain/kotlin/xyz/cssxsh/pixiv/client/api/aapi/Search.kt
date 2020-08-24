package xyz.cssxsh.pixiv.client.api.aapi

import xyz.cssxsh.pixiv.client.DurationType
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.client.SearchTarget
import xyz.cssxsh.pixiv.client.SortType
import xyz.cssxsh.pixiv.client.data.aapi.IllustsData
import xyz.cssxsh.pixiv.client.data.aapi.NovelsData
import xyz.cssxsh.pixiv.client.data.aapi.WordsData


suspend fun PixivClient.getSearchIllust(
    word: String,
    searchTarget: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    sort: SortType = SortType.DATE_DESC,
    duration: DurationType = DurationType.WITHIN_LAST_DAY,
    filter: String = "for_ios"
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.searchIllust,
    paramsMap = mapOf(
        "word" to word,
        "search_target" to searchTarget,
        "sort" to sort,
        "duration" to duration,
        "filter" to filter
    )
)

suspend fun PixivClient.getSearchNovel(
    word: String,
    searchTarget: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    sort: SortType = SortType.DATE_DESC,
    duration: DurationType = DurationType.WITHIN_LAST_DAY,
    filter: String = "for_ios"
): NovelsData = httpGet(
    deserialize = NovelsData.serializer(),
    apiUrl = AppApiUrls.searchNovel,
    paramsMap = mapOf(
        "word" to word,
        "search_target" to searchTarget,
        "sort" to sort,
        "duration" to duration,
        "filter" to filter
    )
)

suspend fun PixivClient.getSearchAutoComplete(
    word: String
): WordsData = httpGet(
    deserialize = WordsData.serializer(),
    apiUrl = AppApiUrls.searchAutoComplete,
    paramsMap = mapOf(
        "word" to word
    )
)
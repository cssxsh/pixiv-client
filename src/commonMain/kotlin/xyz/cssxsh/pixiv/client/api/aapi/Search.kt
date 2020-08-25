package xyz.cssxsh.pixiv.client.api.aapi

import xyz.cssxsh.pixiv.client.DurationType
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.client.SearchTarget
import xyz.cssxsh.pixiv.client.SortType
import xyz.cssxsh.pixiv.client.data.aapi.IllustsData
import xyz.cssxsh.pixiv.client.data.aapi.NovelsData
import xyz.cssxsh.pixiv.client.data.aapi.WordsData

suspend fun PixivClient.searchIllust(
    word: String,
    searchTarget: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    sort: SortType = SortType.DATE_DESC,
    duration: DurationType? = null,
    filter: String = "for_ios",
    offset: Long = 0
): IllustsData = httpGet(
    deserialize = IllustsData.serializer(),
    apiUrl = AppApiUrls.searchIllust,
    paramsMap = mapOf(
        "word" to word,
        "search_target" to searchTarget.value(),
        "sort" to sort.value(),
        "duration" to duration?.value(),
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.searchNovel(
    word: String,
    searchTarget: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    sort: SortType = SortType.DATE_DESC,
    duration: DurationType? = null,
    filter: String = "for_ios",
    offset: Long = 0
): NovelsData = httpGet(
    deserialize = NovelsData.serializer(),
    apiUrl = AppApiUrls.searchNovel,
    paramsMap = mapOf(
        "word" to word,
        "search_target" to searchTarget.value(),
        "sort" to sort.value(),
        "duration" to duration?.value(),
        "filter" to filter,
        "offset" to offset
    )
)

suspend fun PixivClient.searchAutoComplete(
    word: String
): WordsData = httpGet(
    deserialize = WordsData.serializer(),
    apiUrl = AppApiUrls.searchAutoComplete,
    paramsMap = mapOf(
        "word" to word
    )
)
package xyz.cssxsh.pixiv.api.app

import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.Method
import xyz.cssxsh.pixiv.SearchTarget
import xyz.cssxsh.pixiv.SortType
import xyz.cssxsh.pixiv.DurationType
import xyz.cssxsh.pixiv.data.app.IllustData
import xyz.cssxsh.pixiv.data.app.NovelData
import xyz.cssxsh.pixiv.data.app.KeywordsData

suspend fun PixivClient.searchIllust(
    word: String,
    searchTarget: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    sort: SortType = SortType.DATE_DESC,
    duration: DurationType? = null,
    filter: String = "for_ios",
    offset: Long = 0
): IllustData = useRESTful(
    method = Method.GET,
    deserializer = IllustData.serializer(),
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
): NovelData = useRESTful(
    method = Method.GET,
    deserializer = NovelData.serializer(),
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
): KeywordsData = useRESTful(
    method = Method.GET,
    deserializer = KeywordsData.serializer(),
    apiUrl = AppApiUrls.searchAutoComplete,
    paramsMap = mapOf(
        "word" to word
    )
)
package xyz.cssxsh.pixiv.api.apps

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.apps.*

suspend fun PixivClient.searchIllust(
    word: String,
    target: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    sort: SortType = SortType.DATE_DESC,
    duration: DurationType? = null,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = SEARCH_ILLUST,
): IllustData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
        parameter("search_target", target.value())
        parameter("sort", sort.value())
        parameter("duration", duration?.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.searchNovel(
    word: String,
    target: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    sort: SortType = SortType.DATE_DESC,
    duration: DurationType? = null,
    filter: String = "for_ios",
    offset: Long = 0,
    url: String = SEARCH_NOVEL,
): NovelData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
        parameter("search_target", target.value())
        parameter("sort", sort.value())
        parameter("duration", duration?.value())
        parameter("filter", filter)
        parameter("offset", offset)
    }
}

suspend fun PixivClient.searchAutoComplete(
    word: String,
    url: String = SEARCH_AUTO_COMPLETE,
): KeywordsData = useHttpClient { client ->
    client.get(url) {
        parameter("word", word)
    }
}
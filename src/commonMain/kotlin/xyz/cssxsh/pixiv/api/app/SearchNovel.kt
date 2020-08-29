package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.data.app.NovelData

@Serializable
data class SearchNovel(
    override val data: SearchData,
    override val url: String = AppApiUrls.searchIllust,
    override val method: String = "GET",
    override val result: NovelData? = null
): AppRESTful<SearchData, NovelData> {

    override fun copyResult(result: NovelData): SearchNovel =
        copy(result = result)
}
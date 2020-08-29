package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.data.app.KeywordsData

@Serializable
data class SearchAutoComplete(
    override val data: OnlyWord,
    override val url: String = AppApiUrls.searchIllust,
    override val method: String = "GET",
    override val result: KeywordsData? = null
): AppRESTful<OnlyWord, KeywordsData> {

    override fun copyResult(result: KeywordsData): SearchAutoComplete =
        copy(result = result)
}
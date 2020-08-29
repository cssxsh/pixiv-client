package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.data.app.IllustData

@Serializable
data class SearchIllust(
    override val data: SearchData,
    override val url: String = AppApiUrls.searchIllust,
    override val method: String = "GET",
    override val result: IllustData? = null
): AppRESTful<SearchData, IllustData> {

    override fun copyResult(result: IllustData): SearchIllust =
        copy(result = result)
}
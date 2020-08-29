package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.DurationType
import xyz.cssxsh.pixiv.SearchTarget
import xyz.cssxsh.pixiv.SortType

@Serializable
data class SearchData(
    @SerialName("word")
    val word: String,
    @SerialName("search_target")
    val searchTarget: SearchTarget = SearchTarget.EXACT_MATCH_FOR_TAGS,
    @SerialName("sort")
    val sort: SortType = SortType.DATE_DESC,
    @SerialName("duration")
    val duration: DurationType? = null,
    @SerialName("filter")
    val filter: String = "for_ios",
    @SerialName("offset")
    val offset: Long = 0
)
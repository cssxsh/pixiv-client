package xyz.cssxsh.pixiv.publics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    @SerialName("current")
    val current: Int,
    @SerialName("next")
    val next: Int?,
    @SerialName("pages")
    val pages: Int,
    @SerialName("per_page")
    val perPageNum: Int,
    @SerialName("previous")
    val previous: Int?,
    @SerialName("total")
    val total: Int,
)
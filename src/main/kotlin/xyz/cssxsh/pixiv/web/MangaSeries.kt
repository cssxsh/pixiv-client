package xyz.cssxsh.pixiv.web

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
public data class MangaSeries(
    @SerialName("caption")
    val caption: String,
    @SerialName("content_order")
    val contentOrder: Int?,
    @SerialName("coverImageSl")
    val coverImageSl: Int,
    @SerialName("createDate")
    val createAt: String,
    @SerialName("description")
    val description: String,
    @SerialName("firstIllustId")
    val firstIllustId: Long,
    @SerialName("id")
    val id: Long,
    @SerialName("isNotifying")
    val isNotifying: Boolean,
    @SerialName("isWatched")
    val isWatched: Boolean,
    @SerialName("latestIllustId")
    val latestIllustId: Long,
    @SerialName("title")
    val title: String,
    @SerialName("total")
    val total: Int,
    @SerialName("updateDate")

    val updateAt: String,
    @SerialName("url")
    val url: String,
    @SerialName("userId")
    val uid: Long,
    @SerialName("watchCount")
    val watchCount: Long? = null
)
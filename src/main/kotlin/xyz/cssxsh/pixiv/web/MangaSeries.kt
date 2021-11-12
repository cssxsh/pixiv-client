package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*
import java.time.*

@Serializable
data class MangaSeries(
    @SerialName("caption")
    val caption: String,
    @SerialName("content_order")
    val contentOrder: Int?,
    @SerialName("coverImageSl")
    val coverImageSl: Int,
    @SerialName("createDate")
    @Contextual
    val createDate: OffsetDateTime,
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
    @Contextual
    val updateDate: OffsetDateTime,
    @SerialName("url")
    val url: String,
    @SerialName("userId")
    val userId: Long,
    @SerialName("watchCount")
    val watchCount: Long?
)
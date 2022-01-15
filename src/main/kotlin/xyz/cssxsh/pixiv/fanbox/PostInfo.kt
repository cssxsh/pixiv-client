package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*
import java.time.*

@Serializable
data class PostInfo(
    @SerialName("coverImageUrl")
    val coverImageUrl: String?,
    @SerialName("creatorId")
    val creatorId: String,
    @SerialName("excerpt")
    val excerpt: String,
    @SerialName("feeRequired")
    val feeRequired: Int,
    @SerialName("hasAdultContent")
    val hasAdultContent: Boolean,
    @SerialName("id")
    val id: Long,
    @SerialName("imageForShare")
    val imageForShare: String? = null,
    @Contextual
    @SerialName("publishedDatetime")
    val publishedDatetime: OffsetDateTime,
    @SerialName("title")
    val title: String,
    @SerialName("user")
    val user: CreatorInfo
)
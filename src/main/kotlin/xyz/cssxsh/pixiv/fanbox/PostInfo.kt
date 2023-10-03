package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
public data class PostInfo(
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

    @SerialName("publishedDatetime")
    val publishedDatetime: String,
    @SerialName("title")
    val title: String,
    @SerialName("user")
    val user: CreatorInfo
)
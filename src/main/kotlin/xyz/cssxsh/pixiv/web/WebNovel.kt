package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*
import java.time.*

@Serializable
data class WebNovel(
    @SerialName("bookmarkCount")
    val bookmarkCount: Int,
    @SerialName("bookmarkData")
    val bookmarkData: JsonElement,// TODO
    @SerialName("createDate")
    @Contextual
    val createDate: OffsetDateTime,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: Long,
    @SerialName("isBookmarkable")
    val isBookmarkAble: Boolean,
    @SerialName("isMasked")
    val isMasked: Boolean,
    @SerialName("isOriginal")
    val isOriginal: Boolean,
    @SerialName("isUnlisted")
    val isUnlisted: Boolean,
    @SerialName("marker")
    val marker: JsonElement,// TODO
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("restrict")
    @Serializable(PublicityType.Companion.TypeSerializer::class)
    val publicity: PublicityType,
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("textCount")
    val textCount: Long,
    @SerialName("title")
    val title: String,
    @SerialName("titleCaptionTranslation")
    val caption: WebTitleCaption,
    @SerialName("updateDate")
    @Contextual
    val updateDate: OffsetDateTime,
    @SerialName("url")
    val url: String,
    @SerialName("userId")
    val uid: Long,
    @SerialName("userName")
    val name: String,
    @SerialName("xRestrict")
    @Serializable(with = AgeLimit.Companion.TypeSerializer::class)
    val age: AgeLimit,
) : WebWorkInfo
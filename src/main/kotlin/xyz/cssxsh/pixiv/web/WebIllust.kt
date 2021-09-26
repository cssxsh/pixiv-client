package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*
import java.time.*

@Serializable
data class WebIllust(
    @SerialName("alt")
    val alt: String,
    @SerialName("bookmarkData")
    val bookmarkData: JsonElement,// TODO
    @SerialName("createDate")
    @Contextual
    val createDate: OffsetDateTime,
    @SerialName("description")
    val description: String,
    @SerialName("height")
    val height: Int,
    @SerialName("id")
    val id: Long,
    @SerialName("illustType")
    @Serializable(WorkContentType.Companion.TypeSerializer::class)
    val type: WorkContentType,
    @SerialName("isBookmarkable")
    val isBookmarkAble: Boolean,
    @SerialName("isMasked")
    val isMasked: Boolean,
    @SerialName("isUnlisted")
    val isUnlisted: Boolean,
    @SerialName("pageCount")
    val pageCount: Int,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("restrict")
    @Serializable(PublicityType.Companion.TypeSerializer::class)
    val publicity: PublicityType,
    @SerialName("sl")
    @Serializable(SanityLevel.Companion.TypeSerializer::class)
    val sanityLevel: SanityLevel,
    @SerialName("tags")
    val tags: List<String>,
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
    @SerialName("width")
    val width: Int,
    @Serializable(with = AgeLimit.Companion.TypeSerializer::class)
    val age: AgeLimit,
) : WebWorkInfo
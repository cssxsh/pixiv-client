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
    val createAt: OffsetDateTime,
    @SerialName("description")
    val description: String,
    @SerialName("width")
    val width: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("id")
    val pid: Long,
    @SerialName("illustType")
    @Serializable(with = WorkContentType.IndexSerializer::class)
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
    @Serializable(with = PublicityType.IndexSerializer::class)
    val publicity: PublicityType,
    @SerialName("sl")
    val sanityLevel: SanityLevel,
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("title")
    val title: String,
    @SerialName("titleCaptionTranslation")
    val caption: WebTitleCaption,
    @SerialName("updateDate")
    @Contextual
    val updateAt: OffsetDateTime,
    @SerialName("url")
    val url: String,
    @SerialName("userId")
    val uid: Long,
    @SerialName("userName")
    val name: String,
    @SerialName("xRestrict")
    val age: AgeLimit,
) : WebWorkInfo
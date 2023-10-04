package xyz.cssxsh.pixiv.web

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.AgeLimit
import xyz.cssxsh.pixiv.PublicityType


@Serializable
public data class WebNovel(
    @SerialName("bookmarkCount")
    val bookmarkCount: Int,
    @SerialName("bookmarkData")
    val bookmarkData: JsonElement,// TODO
    @SerialName("createDate")

    val createAt: String,
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
    @Serializable(with = PublicityType.IndexSerializer::class)
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

    val updateAt: String,
    @SerialName("url")
    val url: String,
    @SerialName("userId")
    val uid: Long,
    @SerialName("userName")
    val name: String,
    @SerialName("xRestrict")
    val age: AgeLimit,
) : WebWorkInfo
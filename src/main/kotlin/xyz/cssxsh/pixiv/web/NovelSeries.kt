package xyz.cssxsh.pixiv.web

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.AgeLimit


@Serializable
public data class NovelSeries(
    @SerialName("caption")
    val caption: String,
    @SerialName("createDate")
    val createAt: String,
    @SerialName("createdTimestamp")
    val createdTimestamp: Int,
    @SerialName("displaySeriesContentCount")
    val displaySeriesContentCount: Int,
    @SerialName("firstEpisode")
    val firstEpisode: Episode,
    @SerialName("firstNovelId")
    val firstNovelId: Long,
    @SerialName("genreId")
    val genreId: Long,
    @SerialName("id")
    val id: Long,
    @SerialName("isConcluded")
    val isConcluded: Boolean,
    @SerialName("isNotifying")
    val isNotifying: Boolean,
    @SerialName("isOriginal")
    val isOriginal: Boolean,
    @SerialName("isWatched")
    val isWatched: Boolean,
    @SerialName("lastPublishedContentTimestamp")
    val lastPublishedContentTimestamp: Long,
    @SerialName("latestNovelId")
    val latestNovelId: Long,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("publishedContentCount")
    val publishedContentCount: Int,
    @SerialName("publishedTotalCharacterCount")
    val publishedTotalCharacterCount: Int,
    @SerialName("shareText")
    val shareText: String,
    @SerialName("title")
    val title: String,
    @SerialName("total")
    val total: Int,
    @SerialName("updateDate")

    val updateAt: String,
    @SerialName("updatedTimestamp")
    val updatedTimestamp: Long,
    @SerialName("userId")
    val uid: Long,
    @SerialName("userName")
    val name: String,
    @SerialName("watchCount")
    val watchCount: Long?,
    @SerialName("xRestrict")
    val age: AgeLimit
) {
    @Serializable
    public data class Episode(
        @SerialName("url")
        val url: String
    )
}
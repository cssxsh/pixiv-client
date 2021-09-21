package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.AgeLimit
import java.time.OffsetDateTime

@Serializable
data class NovelSeries(
    @SerialName("caption")
    val caption: String,
    @SerialName("createDate")
    val createDate: String,
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
    @Contextual
    val updateDate: OffsetDateTime,
    @SerialName("updatedTimestamp")
    val updatedTimestamp: Long,
    @SerialName("userId")
    val userId: Long,
    @SerialName("userName")
    val userName: String,
    @SerialName("watchCount")
    val watchCount: Long?,
    @SerialName("xRestrict")
    @Serializable(AgeLimit.Companion.TypeSerializer::class)
    val ageLimit: AgeLimit
) {
    @Serializable
    data class Episode(
        @SerialName("url")
        val url: String
    )
}
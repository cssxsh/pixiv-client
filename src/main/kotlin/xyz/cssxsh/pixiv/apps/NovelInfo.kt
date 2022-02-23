package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*
import java.time.*

@Serializable
public data class NovelInfo(
    @SerialName("caption")
    val caption: String,
    @SerialName("create_date")
    @Contextual
    val createAt: OffsetDateTime,
    @SerialName("text_length")
    val textLength: Long,
    @SerialName("id")
    val pid: Long,
    @SerialName("image_urls")
    val imageUrls: FileUrls,
    @SerialName("is_bookmarked")
    val isBookmarked: Boolean,
    @SerialName("is_muted")
    val isMuted: Boolean,
    @SerialName("is_mypixiv_only")
    val isMyPixivOnly: Boolean,
    @SerialName("is_original")
    val isOriginal: Boolean,
    @SerialName("is_x_restricted")
    val isAgeLimit: Boolean,
    @SerialName("page_count")
    val pageCount: Int,
    @SerialName("restrict")
    @Serializable(with = PublicityType.IndexSerializer::class)
    val restrict: PublicityType,
    @SerialName("series")
    val series: SeriesInfo?,
    @SerialName("tags")
    val tags: List<TagInfo>,
    @SerialName("title")
    val title: String,
    @SerialName("total_bookmarks")
    val totalBookmarks: Long? = null,
    @SerialName("total_comments")
    val totalComments: Long? = null,
    @SerialName("total_view")
    val totalView: Long? = null,
    @SerialName("user")
    val user: UserInfo,
    @SerialName("visible")
    val visible: Boolean,
    @SerialName("x_restrict")
    val age: AgeLimit,
)
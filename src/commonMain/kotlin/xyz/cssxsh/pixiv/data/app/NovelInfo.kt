package xyz.cssxsh.pixiv.data.app

import com.soywiz.klock.KlockLocale
import com.soywiz.klock.PatternDateFormat
import com.soywiz.klock.locale.japanese
import com.soywiz.klock.wrapped.WDateTimeTz
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.FileUrls
import xyz.cssxsh.pixiv.data.DateTimeSerializer

@Serializable
data class NovelInfo(
    @SerialName("caption")
    val caption: String,
    @SerialName("create_date")
    @Serializable(with = CreateDateSerializer::class)
    val createDate: WDateTimeTz,
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
    val isXRestricted: Boolean,
    @SerialName("page_count")
    val pageCount: Int,
    @SerialName("restrict")
    val restrict: Int,
    @SerialName("series")
    val series: SeriesInfo?,
    @SerialName("tags")
    val tags: List<TagInfo>,
    @SerialName("title")
    val title: String,
    @SerialName("total_bookmarks")
    val totalBookmarks: Int,
    @SerialName("total_comments")
    val totalComments: Int,
    @SerialName("total_view")
    val totalView: Int,
    @SerialName("text_length")
    val textLength: Long,
    @SerialName("user")
    val user: UserInfo,
    @SerialName("visible")
    val visible: Boolean,
    @SerialName("x_restrict")
    val xRestrict: Int
) {
    companion object {
        object CreateDateSerializer : DateTimeSerializer(
            PatternDateFormat(
                format = "yyyy-MM-dd'T'HH:mm:ssXXX",
                KlockLocale.japanese
            )
        )
    }
}
package xyz.cssxsh.pixiv.data.app

import com.soywiz.klock.KlockLocale
import com.soywiz.klock.PatternDateFormat
import com.soywiz.klock.locale.japanese
import com.soywiz.klock.wrapped.WDateTimeTz
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.ContentType
import xyz.cssxsh.pixiv.FileUrls
import xyz.cssxsh.pixiv.data.DateTimeSerializer

@Serializable
data class IllustInfo(
    @SerialName("caption")
    val caption: String,
    @SerialName("create_date")
    @Serializable(with = CreateDateSerializer::class)
    val createDate: WDateTimeTz,
    @SerialName("height")
    val height: Int,
    @SerialName("id")
    val pid: Long,
    @SerialName("image_urls")
    val imageUrls: FileUrls,
    @SerialName("is_bookmarked")
    val isBookmarked: Boolean,
    @SerialName("is_muted")
    val isMuted: Boolean,
    @SerialName("meta_pages")
    val metaPages: List<MetaPageData>,
    @SerialName("meta_single_page")
    val metaSinglePage: FileUrls,
    @SerialName("page_count")
    val pageCount: Int,
    @SerialName("restrict")
    val restrict: Int,
    @SerialName("sanity_level")
    val sanityLevel: Int,
    @SerialName("series")
    val series: SeriesInfo?,
    @SerialName("tags")
    val tags: List<TagInfo>,
    @SerialName("title")
    val title: String,
    @SerialName("tools")
    val tools: List<String>,
    @SerialName("total_bookmarks")
    val totalBookmarks: Int? = null,
    @SerialName("total_comments")
    val totalComments: Int? = null,
    @SerialName("total_view")
    val totalView: Int? = null,
    @SerialName("type")
    val type: ContentType,
    @SerialName("user")
    val user: UserInfo,
    @SerialName("visible")
    val visible: Boolean,
    @SerialName("width")
    val width: Int,
    @SerialName("x_restrict")
    val xRestrict: Int
) {
    @Serializable
    data class MetaPageData(
        @SerialName("image_urls")
        val imageUrls: FileUrls
    )

    fun getImageUrls(): List<FileUrls> =
        if (pageCount == 1) listOf(metaSinglePage + imageUrls) else metaPages.map { it.imageUrls }

    companion object {
        object CreateDateSerializer : DateTimeSerializer(
            PatternDateFormat(
                format = "yyyy-MM-dd'T'HH:mm:ssXXX",
                KlockLocale.japanese
            )
        )
    }
}
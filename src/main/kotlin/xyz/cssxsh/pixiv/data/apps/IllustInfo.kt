package xyz.cssxsh.pixiv.data.apps

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.WorkContentType
import xyz.cssxsh.pixiv.FileUrls
import xyz.cssxsh.pixiv.data.JapanDateTimeSerializer
import java.time.OffsetDateTime

@Serializable
data class IllustInfo(
    @SerialName("caption")
    val caption: String,
    @SerialName("create_date")
    @Serializable(with = JapanDateTimeSerializer::class)
    val createDate: OffsetDateTime,
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
    val totalBookmarks: Long? = null,
    @SerialName("total_comments")
    val totalComments: Long? = null,
    @SerialName("total_view")
    val totalView: Long? = null,
    @SerialName("type")
    val type: WorkContentType,
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

    fun getOriginImageUrls(): List<String> = getImageUrls().map { urls ->
        urls.filter { "origin" in it.key }.values.first()
    }.sorted()
}
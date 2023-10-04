package xyz.cssxsh.pixiv.apps

import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.*


@Serializable
public data class IllustInfo(
    @SerialName("caption")
    val caption: String,
    @SerialName("create_date")
    val createAt: String,
    @SerialName("width")
    val width: Int,
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
    @Serializable(with = PublicityType.IndexSerializer::class)
    val restrict: PublicityType,
    @SerialName("sanity_level")
    val sanityLevel: SanityLevel,
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
    @SerialName("x_restrict")
    val age: AgeLimit,
    @SerialName("illust_ai_type")
    val ai: Int = 1,
    @SerialName("illust_book_style")
    val bookStyle: Int = 0,
    @SerialName("comment_access_control")
    val commentAccessControl: Int = 0,
) {
    @Serializable
    public data class MetaPageData(
        @SerialName("image_urls")
        val imageUrls: FileUrls,
    )

    public fun getImageUrls(): List<FileUrls> =
        if (pageCount == 1) listOf(metaSinglePage + imageUrls) else metaPages.map { it.imageUrls }

    public fun getOriginImageUrls(): List<Url> = getImageUrls().map { urls ->
        Url(requireNotNull(urls.entries.find { "origin" in it.key }) { "Not Found origin $urls" }.value)
    }
}
package xyz.cssxsh.pixiv.apps

import io.ktor.http.*
import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*
import java.time.*

@Serializable
data class IllustInfo(
    @SerialName("caption")
    val caption: String,
    @SerialName("create_date")
    @Contextual
    val createAt: OffsetDateTime,
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
    @Serializable(with = PublicityType.Companion.TypeSerializer::class)
    val restrict: PublicityType,
    @SerialName("sanity_level")
    @Serializable(with = SanityLevel.Companion.TypeSerializer::class)
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
    @SerialName("width")
    val width: Int,
    @SerialName("x_restrict")
    @Serializable(with = AgeLimit.Companion.TypeSerializer::class)
    val age: AgeLimit,
) {
    @Serializable
    data class MetaPageData(
        @SerialName("image_urls")
        val imageUrls: FileUrls,
    )

    fun getImageUrls(): List<FileUrls> =
        if (pageCount == 1) listOf(metaSinglePage + imageUrls) else metaPages.map { it.imageUrls }

    fun getOriginImageUrls(): List<Url> = getImageUrls().map { urls ->
        Url(requireNotNull(urls.entries.find { "origin" in it.key }) { "Not Found origin $urls" }.value)
    }
}
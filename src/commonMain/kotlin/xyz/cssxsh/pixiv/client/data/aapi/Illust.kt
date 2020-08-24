package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.client.data.FileUrls

@Serializable
data class Illust(
    @SerialName("caption")
    val caption: String,
    @SerialName("create_date")
    val createDate: String,
    @SerialName("height")
    val height: Int,
    @SerialName("id")
    val pid: Int,
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
    val series: Series?,
    @SerialName("tags")
    val tagInfos: List<TagInfo>,
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
    val type: String,
    @SerialName("user")
    val userInfo: UserInfo,
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
}
package xyz.cssxsh.pixiv.client.data.aapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.client.data.FileUrls

@Serializable
data class Novel(
    @SerialName("caption")
    val caption: String,
    @SerialName("create_date")
    val createDate: String,
    @SerialName("id")
    val pid: Int,
    @SerialName("image_urls")
    val imageUrls: FileUrls,
    @SerialName("is_bookmarked")
    val isBookmarked: Boolean,
    @SerialName("is_muted")
    val isMuted: Boolean,
    @SerialName("is_mypixiv_only")
    val isMypixivOnly: Boolean,
    @SerialName("is_original")
    val isOriginal: Boolean,
    @SerialName("is_x_restricted")
    val isXRestricted: Boolean,
    @SerialName("page_count")
    val pageCount: Int,
    @SerialName("restrict")
    val restrict: Int,
    @SerialName("series")
    val series: Series?,
    @SerialName("tags")
    val tagInfos: List<TagInfo>,
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
    val userInfo: UserInfo,
    @SerialName("visible")
    val visible: Boolean,
    @SerialName("x_restrict")
    val xRestrict: Int
) {
    @Serializable
    data class MetaPageData(
        @SerialName("image_urls")
        val imageUrls: FileUrls
    )
}
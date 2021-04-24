package xyz.cssxsh.pixiv.publics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.PublicityType

@Serializable
data class FavoriteWorkData(
    @SerialName("status")
    val status: String,
    @SerialName("response")
    val results: List<FavoriteWorkInfo>,
    @SerialName("count")
    val count: Int,
) {
    @Serializable
    data class FavoriteWorkInfo(
        @SerialName("id")
        val favoriteId: Long,
        @SerialName("work")
        val work: WorkInfo,
        @SerialName("comment")
        val comment: String,
        @SerialName("tags")
        val tags: List<String>,
        @SerialName("publicity")
        val publicity: PublicityType,
    ) {
        @Serializable
        data class WorkInfo(
            @SerialName("id")
            val pid: Long,
        )
    }
}
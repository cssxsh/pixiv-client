package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
data class BookmarkCount(
    @SerialName("private")
    val `private`: Count,
    @SerialName("public")
    val `public`: Count,
) {
    @Serializable
    data class Count(
        @SerialName("illust")
        val illust: Int,
        @SerialName("novel")
        val novel: Int,
    )
}
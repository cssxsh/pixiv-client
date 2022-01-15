package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
data class CreatorDetail(
    @SerialName("coverImageUrl")
    val coverImageUrl: String?,
    @SerialName("creatorId")
    val creatorId: String,
    @SerialName("description")
    val description: String,
    @SerialName("hasAdultContent")
    val hasAdultContent: Boolean,
    @SerialName("hasBoothShop")
    val hasBoothShop: Boolean,
    @SerialName("isAcceptingRequest")
    val isAcceptingRequest: Boolean,
    @SerialName("isFollowed")
    val isFollowed: Boolean,
    @SerialName("isStopped")
    val isStopped: Boolean,
    @SerialName("isSupported")
    val isSupported: Boolean,
    @SerialName("profileItems")
    val profileItems: List<ProfileItem>,
    @SerialName("profileLinks")
    val profileLinks: List<String>,
    @SerialName("user")
    val user: CreatorInfo
) {

    @Serializable
    sealed class ProfileItem(val type: String) {

        @SerialName("id")
        abstract val id: String

        @Serializable
        @SerialName("image")
        data class Image(
            @SerialName("id")
            override val id: String,
            @SerialName("imageUrl")
            val imageUrl: String,
            @SerialName("thumbnailUrl")
            val thumbnailUrl: String
        ) : ProfileItem(type = "image")

        @Serializable
        @SerialName("video")
        data class Video(
            @SerialName("id")
            override val id: String,
            @SerialName("serviceProvider")
            val serviceProvider: String,
            @SerialName("videoId")
            val videoId: String
        ) : ProfileItem(type = "video")
    }
}
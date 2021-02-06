package xyz.cssxsh.pixiv.data.publics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteUserData(
    @SerialName("msg")
    val message: String,
    @SerialName("status")
    val status: String,
    @SerialName("response")
    val results: List<FavoriteUserInfo>,
    @SerialName("count")
    val count: Int,
) {
    @Serializable
    data class FavoriteUserInfo(
        @SerialName("id")
        val id: String,
        @SerialName("target_user")
        val targetUser: User,
    ) {
        @Serializable
        data class User(
            @SerialName("account")
            val account: String,
            @SerialName("id")
            val uid: Long,
            @SerialName("name")
            val name: String,
            @SerialName("status")
            val status: String,
        )
    }
}
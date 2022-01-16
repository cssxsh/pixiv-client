package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaData(
    @SerialName("apiUrl")
    val apiUrl: String,
    @SerialName("context")
    val context: Context,
    @SerialName("csrfToken")
    val csrfToken: String,
    @SerialName("isOnCc")
    val isOnCc: Boolean,
    @SerialName("isPayPalCashbackCampaignForSimplifiedChineseUserEnabled")
    val isPayPalCashbackCampaignForSimplifiedChineseUserEnabled: Boolean,
    @SerialName("isXEmbedEnabled")
    val isXEmbedEnabled: Boolean,
    @SerialName("urlContext")
    val urlContext: UrlContext,
    @SerialName("wwwUrl")
    val wwwUrl: String
) {

    @Serializable
    data class Context(
        @SerialName("user")
        val user: UserSetting
    )

    @Serializable
    data class UrlContext(
        @SerialName("creatorOriginPattern")
        val creatorOriginPattern: String,
        @SerialName("host")
        val host: Host,
        @SerialName("rootOriginPattern")
        val rootOriginPattern: String,
        @SerialName("user")
        val user: User
    ) {
        @Serializable
        data class Host(
            @SerialName("creatorId")
            val creatorId: String?
        )

        @Serializable
        data class User(
            @SerialName("creatorId")
            val creatorId: String?,
            @SerialName("isLoggedIn")
            val isLoggedIn: Boolean
        )
    }
}
package xyz.cssxsh.pixiv.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.FileUrls

@Serializable
data class AuthResult(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("device_token")
    val deviceToken: String? = "",
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("scope")
    val scope: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("user")
    val user: User,
    @SerialName("response")
    val info: AuthInfo
) {
    @Serializable
    data class AuthInfo(
        @SerialName("access_token")
        val accessToken: String,
        @SerialName("device_token")
        val deviceToken: String? = "",
        @SerialName("expires_in")
        val expiresIn: Int,
        @SerialName("refresh_token")
        val refreshToken: String,
        @SerialName("scope")
        val scope: String,
        @SerialName("token_type")
        val tokenType: String,
        @SerialName("user")
        val user: User
    )
    @Serializable
    data class User(
        @SerialName("account")
        val account: String,
        @SerialName("id")
        val uid: Long,
        @SerialName("is_mail_authorized")
        val isMailAuthorized: Boolean,
        @SerialName("is_premium")
        val isPremium: Boolean,
        @SerialName("mail_address")
        val mailAddress: String? = null,
        @SerialName("name")
        val name: String,
        @SerialName("profile_image_urls")
        val profileFileUrls: FileUrls,
        @SerialName("x_restrict")
        val xRestrict: Int
    )
}
package xyz.cssxsh.pixiv.auth

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*

@Serializable
data class AuthResult(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("device_token")
    val deviceToken: String? = null,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("scope")
    val scope: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("user")
    val user: User,
//    @SerialName("require_policy_agreement")
//    val require: Boolean = false,
//    @SerialName("response")
//    private val info: AuthInfo,
) {

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
        @Serializable(with = AgeLimit.Companion.TypeSerializer::class)
        val age: AgeLimit,
    )
}
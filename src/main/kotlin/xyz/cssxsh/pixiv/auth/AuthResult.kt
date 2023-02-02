package xyz.cssxsh.pixiv.auth

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*

@Serializable
public data class AuthResult(
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
    @SerialName("response")
    internal val response: AuthResult? = null,
) {

    @Serializable
    public data class User(
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
        val age: AgeLimit,
        @SerialName("require_policy_agreement")
        val requirePolicyAgreement: Boolean = false,
    )
}
package xyz.cssxsh.pixiv.auth

import kotlinx.serialization.*

@Serializable
data class HtmlAccount(
    @SerialName("pixivAccount.continueWithCurrentAccountUrl")
    val current: String = "",
    @SerialName("pixivAccount.returnTo")
    val returnTo: String,
    @SerialName("pixivAccount.tt")
    val tt: String,
    @SerialName("pixivAccount.userId")
    val uid: Long? = null,
    @SerialName("pixivAccount.ref")
    val ref: String = "",
    @SerialName("pixivAccount.recaptchaEnterpriseScoreSiteKey")
    val scoreSiteKey: String = "",
    @SerialName("pixivAccount.recaptchaEnterpriseScoreAction")
    val scoreAction: String = "",
    @SerialName("pixivAccount.recaptchaEnterpriseCheckboxSiteKey")
    val checkboxSiteKey: String = "",
    @SerialName("pixivAccount.postKey")
    val postKey: String = "",
    @SerialName("pixivAccount.source")
    val source: String = ""
)
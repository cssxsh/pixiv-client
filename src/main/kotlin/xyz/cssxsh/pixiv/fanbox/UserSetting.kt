package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
public data class UserSetting(
    @SerialName("creatorId")
    val creatorId: String?,
    @SerialName("fanboxUserStatus")
    val fanboxUserStatus: Int,
    @SerialName("hasPlans")
    val hasPlans: Boolean,
    @SerialName("hasUnpaidPayments")
    val hasUnpaidPayments: Boolean,
    @SerialName("iconUrl")
    val iconUrl: String?,
    @SerialName("isCreator")
    val isCreator: Boolean,
    @SerialName("isMailAddressOutdated")
    val isMailAddressOutdated: Boolean,
    @SerialName("isPrivacyPolicyAgreementRequired")
    val isPrivacyPolicyAgreementRequired: Boolean,
    @SerialName("isSupporter")
    val isSupporter: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("showAdultContent")
    val showAdultContent: Boolean,
    @SerialName("socialConnectStatus")
    val socialConnectStatus: SocialConnectStatus,
    @SerialName("userId")
    val userId: Long
) {
    @Serializable
    public data class SocialConnectStatus(
        @SerialName("twitter")
        val twitter: Boolean
    )
}
package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*

@Serializable
@Suppress("unused")
public enum class Notification {
    @SerialName("email_important_notices")
    EMAIL_IMPORTANT_NOTICES,

    @SerialName("email_announcements_from_fanbox")
    EMAIL_ANNOUNCEMENTS_FROM_FANBOX,

    @SerialName("email_to_supporter_on_post_published")
    EMAIL_TO_SUPPORTER_ON_POST_PUBLISHED,

    @SerialName("email_to_supporter_on_monthly_charge_success")
    EMAIL_TO_SUPPORTER_ON_MONTHLY_CHARGE_SUCCESS,

    @SerialName("email_to_creator_on_support_start")
    EMAIL_TO_CREATOR_ON_SUPPORT_START,

    @SerialName("email_to_follower_on_post_published")
    EMAIL_TO_FOLLOWER_ON_POST_PUBLISHED,

    @SerialName("email_to_fan_on_newsletter")
    EMAIL_TO_FAN_ON_NEWSLETTER,

    @SerialName("bell_post_like")
    BELL_POST_LIKE,

    @SerialName("bell_post_comment")
    BELL_POST_COMMENT,

    @SerialName("bell_post_comment_like")
    BELL_POST_COMMENT_LIKE,

    @SerialName("bell_support_start")
    BELL_SUPPORT_START,

    @SerialName("bell_new_follower")
    BELL_NEW_FOLLOWER,

    @SerialName("bell_to_supporter_on_post_published")
    BELL_TO_SUPPORTER_ON_POST_PUBLISHED,

    @SerialName("bell_to_follower_on_post_published")
    BELL_TO_FOLLOWER_ON_POST_PUBLISHED
}
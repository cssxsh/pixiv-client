package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class ApplicationData(
    @SerialName("application_info")
    val applicationInfo: ApplicationInfo
) {
    @Serializable
    public data class ApplicationInfo(
        @SerialName("latest_version")
        val latestVersion: String,
        @SerialName("notice_exists")
        val noticeExists: Boolean,
        @SerialName("notice_id")
        val noticeId: String,
        @SerialName("notice_important")
        val noticeImportant: Boolean,
        @SerialName("notice_message")
        val noticeMessage: String,
        @SerialName("store_url")
        val storeUrl: String,
        @SerialName("update_available")
        val updateAvailable: Boolean,
        @SerialName("update_message")
        val updateMessage: String,
        @SerialName("update_required")
        val updateRequired: Boolean
    )
}
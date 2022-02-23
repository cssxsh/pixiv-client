package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*

@Serializable
public data class UserDetail(
    @SerialName("profile")
    val profile: Profile,
    @SerialName("profile_publicity")
    val publicity: ProfilePublicity,
    @SerialName("user")
    val user: UserInfo,
    @SerialName("workspace")
    val workspace: Workspace,
) {
    @Serializable
    public data class Profile(
        @SerialName("address_id")
        val addressId: Int,
        @SerialName("background_image_url")
        val backgroundImageUrl: String?,
        @SerialName("birth")
        val birth: String,
        @SerialName("birth_day")
        val birthDay: String,
        @SerialName("birth_year")
        val birthYear: Int,
        @SerialName("country_code")
        val countryCode: String,
        @SerialName("gender")
        val gender: String,
        @SerialName("is_premium")
        val isPremium: Boolean,
        @SerialName("is_using_custom_profile_image")
        val isUsingCustomProfileImage: Boolean,
        @SerialName("job")
        val job: String,
        @SerialName("job_id")
        val jobId: Int,
        @SerialName("pawoo_url")
        val pawooUrl: String?,
        @SerialName("region")
        val region: String,
        @SerialName("total_follow_users")
        val totalFollowUsers: Long,
        @SerialName("total_illust_bookmarks_public")
        val totalIllustBookmarksPublic: Long,
        @SerialName("total_illust_series")
        val totalIllustSeries: Long,
        @SerialName("total_illusts")
        val totalIllusts: Long,
        @SerialName("total_manga")
        val totalManga: Long,
        @SerialName("total_mypixiv_users")
        val totalMyPixivUsers: Long,
        @SerialName("total_novel_series")
        val totalNovelSeries: Long,
        @SerialName("total_novels")
        val totalNovels: Long,
        @SerialName("twitter_account")
        val twitterAccount: String?,
        @SerialName("twitter_url")
        val twitterUrl: String?,
        @SerialName("webpage")
        val webpage: String?,
    ) {
        public val totalArtwork: Long get() = totalIllusts + totalManga
    }

    @Serializable
    public data class ProfilePublicity(
        @SerialName("birth_day")
        val birthDay: PublicityType,
        @SerialName("birth_year")
        val birthYear: PublicityType,
        @SerialName("gender")
        val gender: PublicityType,
        @SerialName("job")
        val job: PublicityType,
        @SerialName("pawoo")
        val pawoo: Boolean,
        @SerialName("region")
        val region: PublicityType,
    )

    @Serializable
    public data class Workspace(
        @SerialName("chair")
        val chair: String,
        @SerialName("comment")
        val comment: String,
        @SerialName("desk")
        val desk: String,
        @SerialName("desktop")
        val desktop: String,
        @SerialName("monitor")
        val monitor: String,
        @SerialName("mouse")
        val mouse: String,
        @SerialName("music")
        val music: String,
        @SerialName("pc")
        val pc: String,
        @SerialName("printer")
        val printer: String,
        @SerialName("scanner")
        val scanner: String,
        @SerialName("tablet")
        val tablet: String,
        @SerialName("tool")
        val tool: String,
        @SerialName("workspace_image_url")
        val workspaceImageUrl: String?,
    )
}
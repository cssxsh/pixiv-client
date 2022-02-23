package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*

@Serializable
public data class UserProfileAll(
    @SerialName("bookmarkCount")
    val bookmarkCount: Map<PublicityType, BookmarkCount>,
    @SerialName("externalSiteWorksStatus")
    val externalSiteWorksStatus: Map<String, Boolean>,
    @SerialName("illusts")
    @Serializable(WepApiSet::class)
    val illusts: Set<Long>,
    @SerialName("manga")
    @Serializable(WepApiSet::class)
    val mangas: Set<Long>,
    @SerialName("novels")
    @Serializable(WepApiSet::class)
    val novels: Set<Long>,
    @SerialName("mangaSeries")
    val mangaSeries: List<MangaSeries>,
    @SerialName("novelSeries")
    val novelSeries: List<NovelSeries>,
    @SerialName("pickup")
    val pickup: List<UserPickupInfo>,
    @SerialName("request")
    val request: UserRequestInfo,
)
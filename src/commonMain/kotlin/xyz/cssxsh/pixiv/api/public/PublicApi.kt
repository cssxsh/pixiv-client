package xyz.cssxsh.pixiv.api.public

import kotlinx.serialization.json.JsonElement
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.client.*
import xyz.cssxsh.pixiv.data.public.*

suspend fun PixivClient.getRanking(
    mode: String,
    page: Int = 1,
    perPageNum: Int = 30,
    date: String? = null,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf()
): RankingData = useRESTful(
    method = Method.GET,
    deserializer = RankingData.serializer(),
    apiUrl = PublicApiUrls.rankingAll,
    paramsMap = mapOf(
        "mode" to mode,
        "page" to page,
        "per_page" to perPageNum,
        "date" to date,
        "include_stats" to includeStats,
        "include_sanity_level" to includeSanityLevel,
        "image_sizes" to imageSizes,
        "profile_image_sizes" to profileImageSizes
    )
)

suspend fun PixivClient.postFavoriteWorks(
    pid: Long,
    publicity: PublicityType = PublicityType.PUBLIC
): FavoriteWorkData = useRESTful(
    method = Method.POST,
    deserializer = FavoriteWorkData.serializer(),
    apiUrl = PublicApiUrls.meFavoriteWorks,
    paramsMap = mapOf(
        "work_id" to pid,
        "publicity" to publicity.value()
    )
)

suspend fun PixivClient.deleteFavoriteWorks(
    ids: Long
): FavoriteWorkData = useRESTful(
    method = Method.DELETE,
    deserializer = FavoriteWorkData.serializer(),
    apiUrl = PublicApiUrls.meFavoriteWorks,
    paramsMap = mapOf(
        "ids" to ids
    )
)

suspend fun PixivClient.postFavoriteUsers(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC
): FavoriteUserData = useRESTful(
    method = Method.POST,
    deserializer = FavoriteUserData.serializer(),
    apiUrl = PublicApiUrls.meFavoriteUsers,
    paramsMap = mapOf(
        "target_user_id" to uid,
        "publicity" to publicity.value()
    )
)

suspend fun PixivClient.deleteFavoriteUsers(
    uid: Long
): FavoriteUserData = useRESTful(
    method = Method.DELETE,
    deserializer = FavoriteUserData.serializer(),
    apiUrl = PublicApiUrls.meFavoriteWorks,
    paramsMap = mapOf(
        "delete_ids" to uid
    )
)

suspend fun PixivClient.postViewedWorks(
    pid:  Long
): JsonElement = useRESTful(
    method = Method.POST,
    deserializer = JsonElement.serializer(),
    apiUrl = PublicApiUrls.meViewedWorks,
    paramsMap = mapOf(
        "ids" to pid
    )
)

suspend fun PixivClient.getFollowing(
    page: Int = 1,
    perPageNum: Int = 30,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf()
): ListArtData = useRESTful(
    method = Method.GET,
    deserializer = ListArtData.serializer(),
    apiUrl = PublicApiUrls.meFollowingWorks,
    paramsMap = mapOf(
        "page" to page,
        "per_page" to perPageNum,
        "include_stats" to includeStats,
        "include_sanity_level" to includeSanityLevel,
        "image_sizes" to imageSizes,
        "profile_image_sizes" to profileImageSizes
    )
)

suspend fun PixivClient.getFriendsWorks(
    page: Int = 1,
    perPageNum: Int = 30,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf()
): ListArtData = useRESTful(
    method = Method.GET,
    deserializer = ListArtData.serializer(),
    apiUrl = PublicApiUrls.meFriendsWorks,
    paramsMap = mapOf(
        "page" to page,
        "per_page" to perPageNum,
        "include_stats" to includeStats,
        "include_sanity_level" to includeSanityLevel,
        "image_sizes" to imageSizes,
        "profile_image_sizes" to profileImageSizes
    )
)

suspend fun PixivClient.getWorks(
    page: Int = 1,
    perPageNum: Int = 30,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf()
): ListArtData = useRESTful(
    method = Method.GET,
    deserializer = ListArtData.serializer(),
    apiUrl = PublicApiUrls.works,
    paramsMap = mapOf(
        "page" to page,
        "per_page" to perPageNum,
        "include_stats" to includeStats,
        "include_sanity_level" to includeSanityLevel,
        "image_sizes" to imageSizes,
        "profile_image_sizes" to profileImageSizes
    )
)

suspend fun PixivClient.getFeeds(
    type: String = "touch_nottext",
    relation: String = "all",
    showR18: Boolean = false
): JsonElement = useRESTful(
    method = Method.GET,
    deserializer = JsonElement.serializer(),
    apiUrl = PublicApiUrls.meFeeds,
    paramsMap = mapOf(
        "type" to type,
        "relation" to relation,
        "show_r18" to showR18
    )
)

suspend fun PixivClient.getSearchWorks(
    word: String,
    mode: SearchMode = SearchMode.EXACT_TAG,
    sort: SortMode = SortMode.DATE,
    order: OrderType = OrderType.DESC,
    period: PeriodType = PeriodType.ALL,
    page: Int = 1,
    perPageNum: Int = 30,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf()
): ListArtData = useRESTful(
    method = Method.GET,
    deserializer = ListArtData.serializer(),
    apiUrl = PublicApiUrls.searchWorks,
    paramsMap = mapOf(
        "q" to word,
        "mode" to mode.value(),
        "sort" to sort.value(),
        "order" to order.value(),
        "period" to period.value(),
        "page" to page,
        "per_page" to perPageNum,
        "include_stats" to includeStats,
        "include_sanity_level" to includeSanityLevel,
        "image_sizes" to imageSizes,
        "profile_image_sizes" to profileImageSizes
    )
)

/**
 * @param uid 用户ID
 * @param publicity 关注类型 [PublicityType]
 */
suspend fun PixivClient.getUsersFavoriteWorks(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    page: Int = 1,
    perPageNum: Int = 30,
    includeWork: Boolean = true,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf()
): ListArtData = useRESTful(
    method = Method.GET,
    deserializer = ListArtData.serializer(),
    apiUrl = PublicApiUrls.usersFavoriteWorks(uid),
    paramsMap = mapOf(
        "publicity" to publicity.value(),
        "include_work" to includeWork,
        "page" to page,
        "per_page" to perPageNum,
        "include_stats" to includeStats,
        "include_sanity_level" to includeSanityLevel,
        "image_sizes" to imageSizes,
        "profile_image_sizes" to profileImageSizes
    )
)

/**
 * @param uid 用户ID
 * @param publicity 关注类型 [PublicityType]
 */
suspend fun PixivClient.getUserWork(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    page: Int = 1,
    perPageNum: Int = 30,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf()
): ListArtData = useRESTful(
    method = Method.GET,
    deserializer = ListArtData.serializer(),
    apiUrl = PublicApiUrls.usersWorks(uid),
    paramsMap = mapOf(
        "publicity" to publicity.value(),
        "page" to page,
        "per_page" to perPageNum,
        "include_stats" to includeStats,
        "include_sanity_level" to includeSanityLevel,
        "image_sizes" to imageSizes,
        "profile_image_sizes" to profileImageSizes
    )
)

suspend fun PixivClient.worksByPid(
    pid: Long,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf()
): ListArtData = useRESTful(
    method = Method.GET,
    deserializer = ListArtData.serializer(),
    apiUrl = PublicApiUrls.worksByPid(pid),
    paramsMap = mapOf(
        "include_stats" to includeStats,
        "include_sanity_level" to includeSanityLevel,
        "image_sizes" to imageSizes,
        "profile_image_sizes" to profileImageSizes
    )
)

suspend fun PixivClient.userIllusts(
    pid: Long,
    includeStats: Boolean = true,
    includeSanityLevel: Boolean = true,
    imageSizes: List<String> = listOf(),
    profileImageSizes: List<String> = listOf()
): ListArtData = useRESTful(
    method = Method.GET,
    deserializer = ListArtData.serializer(),
    apiUrl = PublicApiUrls.worksByPid(pid),
    paramsMap = mapOf(
        "include_stats" to includeStats,
        "include_sanity_level" to includeSanityLevel,
        "image_sizes" to imageSizes,
        "profile_image_sizes" to profileImageSizes
    )
)
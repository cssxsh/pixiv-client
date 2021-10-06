@file:OptIn(InternalSerializationApi::class, ExperimentalSerializationApi::class)

package xyz.cssxsh.pixiv

import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import xyz.cssxsh.pixiv.exception.*
import java.net.*
import java.util.*

typealias HeadersMap = Map<String, String>

typealias FileUrls = Map<String, String>

val PixivJson = Json {
    ignoreUnknownKeys = true
    allowSpecialFloatingPointValues = true
    serializersModule += SerializersModule {
        contextual(JapanDateTimeSerializer)
    }
}

const val HTTP_KILO = 1022

const val CLIENT_ID: String = "MOBrBDS8blbauoSck0ZfDbtuzpyT"

const val CLIENT_SECRET: String = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"

const val HASH_SECRET: String = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c"

val IOS_HEADERS: HeadersMap = mapOf(
    HttpHeaders.AcceptLanguage to Locale.CHINA.language,
    HttpHeaders.UserAgent to "PixivIOSApp/6.0.4 (iOS 9.0.2; iPhone6,1)",
    "App-OS-Version" to "14.6",
    "App-OS" to "ios"
)

val ANDROID_HEADERS: HeadersMap = mapOf(
    HttpHeaders.AcceptLanguage to Locale.CHINA.language,
    HttpHeaders.UserAgent to "PixivAndroidApp/5.0.64 (Android 6.0)",
    "App-OS-Version" to "6.0",
    "App-OS" to "android"
)

const val JAPAN_DNS: String = "https://public.dns.iij.jp/dns-query"

val DEFAULT_PIXIV_HOST = mapOf(
    "*.pixiv.net" to listOf("api.fanbox.cc"),
    "sketch.pixiv.net" to listOf("pixivsketch.net"),
    "times.pixiv.net" to listOf("domains.tumblr.com"),
    "matsuri.pixiv.net" to listOf("d37vhba44w9rfk.cloudfront.net"),
    "festa.pixiv.net" to listOf("d27o62ujhz6nk8.cloudfront.net"),
    "iracon.pixiv.net" to listOf("dq5nq916rhniu.cloudfront.net"),
    "g-client-proxy.pixiv.net" to listOf("6837361.gigya-api.com", "d1ctzrip8l97jt.cloudfront.net"),
    "dev.pixiv.net" to listOf("s3-website-ap-northeast-1.amazonaws.com")
)

const val NO_PROFILE_IMAGE = "https://s.pximg.net/common/images/no_profile.png"

const val LIMIT_UNKNOWN_IMAGE = "https://s.pximg.net/common/images/limit_unknown_360.png"

internal fun Url.toProxy(): Proxy {
    val type = when (protocol) {
        URLProtocol.SOCKS -> Proxy.Type.SOCKS
        URLProtocol.HTTP -> Proxy.Type.HTTP
        else -> throw ProxyException(this)
    }
    return Proxy(type, InetSocketAddress(host, port))
}

@Suppress("FunctionName")
inline fun <reified T: Enum<T>> PixivEnumSerializer(): KSerializer<T> {
    return object : KSerializer<T> {
        override val descriptor: SerialDescriptor =
            buildSerialDescriptor(T::class.qualifiedName!!, SerialKind.ENUM)

        override fun serialize(encoder: Encoder, value: T) =
            encoder.encodeString(value.name.lowercase())

        override fun deserialize(decoder: Decoder): T =
            enumValueOf(decoder.decodeString().uppercase())
    }
}

@Suppress("FunctionName")
inline fun <reified T: Enum<T>> PixivTypeSerializer(): KSerializer<T>  {
    return object : KSerializer<T> {
        override val descriptor: SerialDescriptor =
            buildSerialDescriptor(T::class.qualifiedName!!, PrimitiveKind.INT)

        override fun serialize(encoder: Encoder, value: T) =
            encoder.encodeInt(value.ordinal)

        override fun deserialize(decoder: Decoder): T =
            requireNotNull(enumValues<T>().getOrNull(decoder.decodeInt())) { "index: ${decoder.decodeInt()} not in ${enumValues<T>()}" }
    }
}

@Serializable(with = OrderType.Companion::class)
enum class OrderType {
    DESC,
    ASC;

    override fun toString(): String = name.lowercase()

    companion object : KSerializer<OrderType> by PixivEnumSerializer()
}

@Serializable(with = SearchSort.Companion::class)
enum class SearchSort {
    DATE_DESC,
    DATE_ASC,

    // FOR PREMIUM
    POPULAR_DESC,
    POPULAR_MALE_DESC,
    POPULAR_FEMALE_DESC;

    override fun toString(): String = name.lowercase()

    companion object : KSerializer<SearchSort> by PixivEnumSerializer()
}

@Serializable(with = SearchDuration.Companion::class)
enum class SearchDuration {
    CUSTOM_DURATION,
    ALL,
    WITHIN_LAST_DAY,
    WITHIN_LAST_WEEK,
    WITHIN_LAST_MONTH,
    WITHIN_HALF_YEAR,
    WITHIN_YEAR,
    SELECT;

    override fun toString(): String = name.lowercase()

    companion object : KSerializer<SearchDuration> by PixivEnumSerializer()
}

@Serializable(with = PublicityType.Companion::class)
enum class PublicityType {
    PUBLIC,
    PRIVATE,
    TEMP;

    override fun toString(): String = name.lowercase()

    companion object : KSerializer<PublicityType> by PixivEnumSerializer() {
        object TypeSerializer : KSerializer<PublicityType> by PixivTypeSerializer()
    }
}

@Serializable(with = SearchTarget.Companion::class)
enum class SearchTarget {
    PARTIAL_MATCH_FOR_TAGS,
    EXACT_MATCH_FOR_TAGS,
    TITLE_AND_CAPTION;

    override fun toString(): String = name.lowercase()

    companion object : KSerializer<SearchTarget> by PixivEnumSerializer()
}

@Serializable(with = WorkContentType.Companion::class)
enum class WorkContentType {
    ILLUST,
    UGOIRA,
    MANGA;

    override fun toString(): String = name.lowercase()

    companion object : KSerializer<WorkContentType> by PixivEnumSerializer() {
        object TypeSerializer : KSerializer<WorkContentType> by PixivTypeSerializer()
    }
}

@Serializable(with = RankMode.Companion::class)
enum class RankMode {
    // MONTH
    MONTH,
    MONTH_MANGA,

    // WEEK
    WEEK,
    WEEK_ORIGINAL,
    WEEK_ROOKIE,
    WEEK_R18,
    WEEK_R18G,
    WEEK_MANGA,
    WEEK_ORIGINAL_MANGA,
    WEEK_ROOKIE_MANGA,
    WEEK_R18_MANGA,
    WEEK_R18G_MANGA,

    // DAY
    DAY,
    DAY_MALE,
    DAY_FEMALE,
    DAY_R18,
    DAY_MALE_R18,
    DAY_FEMALE_R18,
    DAY_MANGA,
    DAY_R18_MANGA,
    DAY_R18G_MANGA;

    override fun toString(): String = name.lowercase()

    companion object : KSerializer<RankMode> by PixivEnumSerializer()
}

@Serializable(with = SanityLevel.Companion::class)
enum class SanityLevel {
    UNCHECKED,
    TEMP1,
    WHITE,
    TEMP3,
    SEMI_BLACK,
    TEMP5,
    BLACK,
    NONE;

    override fun toString(): String = name.lowercase()

    companion object : KSerializer<SanityLevel> by PixivEnumSerializer() {
        object TypeSerializer : KSerializer<SanityLevel> by PixivTypeSerializer()
    }
}

@Serializable(with = AgeLimit.Companion::class)
enum class AgeLimit {
    ALL {
        override fun toString(): String = "all-age"
    },
    R18 {
        override fun toString(): String = "r18"
    },
    R18G {
        override fun toString(): String = "r18-g"
    };

    companion object : KSerializer<AgeLimit> by PixivEnumSerializer() {
        override fun deserialize(decoder: Decoder): AgeLimit = decoder.decodeString().let { value ->
            requireNotNull(values().find { it.toString() == value }) { "$value not in ${values().toList()}" }
        }

        object TypeSerializer : KSerializer<AgeLimit> by PixivTypeSerializer()
    }
}

enum class FilterType {
    FOR_ANDROID,
    FOR_ISO;

    override fun toString(): String = name.lowercase()
}

@Serializable(with = CategoryType.Companion::class)
enum class CategoryType {
    ALL,
    ILLUST,
    MANGA;

    override fun toString(): String = name.lowercase()

    companion object : KSerializer<CategoryType> by PixivEnumSerializer()
}

enum class FollowType {
    SHOW,
    HIDE;

    override fun toString(): String = name.lowercase()
}
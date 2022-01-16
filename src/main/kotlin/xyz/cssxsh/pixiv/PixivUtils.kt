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
    // ignoreUnknownKeys = true
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

const val LIMIT_MYPIXIV_IMAGE = "https://s.pximg.net/common/images/limit_mypixiv_360.png"

internal fun Url.toProxy(): Proxy {
    val type = when (protocol) {
        URLProtocol.SOCKS -> Proxy.Type.SOCKS
        URLProtocol.HTTP -> Proxy.Type.HTTP
        else -> throw ProxyException(this)
    }
    return Proxy(type, InetSocketAddress(host, port))
}

@Suppress("FunctionName")
inline fun <reified T : Enum<T>> EnumNameSerializer(): KSerializer<T> {
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
inline fun <reified T : Enum<T>> EnumIndexSerializer(): KSerializer<T> {
    return object : KSerializer<T> {
        override val descriptor: SerialDescriptor =
            buildSerialDescriptor(T::class.qualifiedName!!, PrimitiveKind.INT)

        override fun serialize(encoder: Encoder, value: T) =
            encoder.encodeInt(value.ordinal)

        override fun deserialize(decoder: Decoder): T =
            requireNotNull(enumValues<T>().getOrNull(decoder.decodeInt())) { "index: ${decoder.decodeInt()} not in ${enumValues<T>()}" }
    }
}

@Serializable(with = OrderType.NameSerializer::class)
enum class OrderType {
    DESC,
    ASC;

    override fun toString(): String = name.lowercase()

    object NameSerializer : KSerializer<OrderType> by EnumNameSerializer()

    object IndexSerializer : KSerializer<OrderType> by EnumIndexSerializer()

    companion object : KSerializer<OrderType> by NameSerializer
}

@Serializable(with = SearchSort.NameSerializer::class)
enum class SearchSort {
    DATE_DESC,
    DATE_ASC,

    // FOR PREMIUM
    POPULAR_DESC,
    POPULAR_MALE_DESC,
    POPULAR_FEMALE_DESC;

    override fun toString(): String = name.lowercase()

    object NameSerializer : KSerializer<SearchSort> by EnumNameSerializer()

    object IndexSerializer : KSerializer<SearchSort> by EnumIndexSerializer()

    companion object : KSerializer<SearchSort> by NameSerializer
}

@Serializable(with = SearchDuration.NameSerializer::class)
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

    object NameSerializer : KSerializer<SearchDuration> by EnumNameSerializer()

    object IndexSerializer : KSerializer<SearchDuration> by EnumIndexSerializer()

    companion object : KSerializer<SearchDuration> by NameSerializer
}

@Serializable(with = PublicityType.NameSerializer::class)
enum class PublicityType {
    PUBLIC,
    PRIVATE,
    MYPIXIV;

    override fun toString(): String = name.lowercase()

    object NameSerializer : KSerializer<PublicityType> by EnumNameSerializer()

    object IndexSerializer : KSerializer<PublicityType> by EnumIndexSerializer()

    companion object : KSerializer<PublicityType> by NameSerializer
}

@Serializable(with = SearchTarget.NameSerializer::class)
enum class SearchTarget {
    PARTIAL_MATCH_FOR_TAGS,
    EXACT_MATCH_FOR_TAGS,
    TITLE_AND_CAPTION;

    override fun toString(): String = name.lowercase()

    object NameSerializer : KSerializer<SearchTarget> by EnumNameSerializer()

    object IndexSerializer : KSerializer<SearchTarget> by EnumIndexSerializer()

    companion object : KSerializer<SearchTarget> by NameSerializer
}

@Serializable(with = WorkContentType.NameSerializer::class)
enum class WorkContentType {
    ILLUST,
    UGOIRA,
    MANGA;

    override fun toString(): String = name.lowercase()

    object NameSerializer : KSerializer<WorkContentType> by EnumNameSerializer()

    object IndexSerializer : KSerializer<WorkContentType> by EnumIndexSerializer()

    companion object : KSerializer<WorkContentType> by NameSerializer
}

@Serializable(with = RankMode.NameSerializer::class)
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

    object NameSerializer : KSerializer<RankMode> by EnumNameSerializer()

    object IndexSerializer : KSerializer<RankMode> by EnumIndexSerializer()

    companion object : KSerializer<RankMode> by NameSerializer
}

@Serializable(with = SanityLevel.IndexSerializer::class)
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

    object NameSerializer : KSerializer<SanityLevel> by EnumNameSerializer()

    object IndexSerializer : KSerializer<SanityLevel> by EnumIndexSerializer()

    companion object : KSerializer<SanityLevel> by IndexSerializer
}

@Serializable(with = AgeLimit.IndexSerializer::class)
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

    object NameSerializer : KSerializer<AgeLimit> by EnumNameSerializer() {
        override fun deserialize(decoder: Decoder): AgeLimit {
            val value = decoder.decodeString()
            return requireNotNull(values().find { it.toString() == value }) { "$value not in ${values().toList()}" }
        }
    }

    object IndexSerializer : KSerializer<AgeLimit> by EnumIndexSerializer()

    companion object : KSerializer<AgeLimit> by IndexSerializer
}

enum class FilterType {
    FOR_ANDROID,
    FOR_ISO;

    override fun toString(): String = name.lowercase()
}

@Serializable(with = CategoryType.NameSerializer::class)
enum class CategoryType {
    ALL,
    ILLUST,
    MANGA;

    override fun toString(): String = name.lowercase()

    object NameSerializer : KSerializer<CategoryType> by EnumNameSerializer()

    object IndexSerializer : KSerializer<CategoryType> by EnumIndexSerializer()

    companion object : KSerializer<CategoryType> by NameSerializer
}

enum class FollowType {
    SHOW,
    HIDE;

    override fun toString(): String = name.lowercase()
}

object RegexSerializer : KSerializer<Regex> {
    override val descriptor: SerialDescriptor = buildSerialDescriptor("kotlin.text.Regex", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Regex {
        return Regex(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Regex) {
        encoder.encodeString(value.pattern)
    }
}

fun Regex.Companion.serializer(): KSerializer<Regex> = RegexSerializer
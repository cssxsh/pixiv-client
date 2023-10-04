package xyz.cssxsh.pixiv

import io.ktor.client.plugins.auth.providers.*
import io.ktor.http.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import xyz.cssxsh.pixiv.auth.AuthResult

public typealias HeadersMap = Map<String, String>

public typealias FileUrls = Map<String, String>

public val PixivJson: Json = Json {
    ignoreUnknownKeys = true
    allowSpecialFloatingPointValues = true
}

public const val HTTP_KILO: Int = 1022

public const val IOS_CLIENT_ID: String = "KzEZED7aC0vird8jWyHM38mXjNTY"

public const val IOS_CLIENT_SECRET: String = "W9JZoJe00qPvJsiyCGT3CCtC6ZUtdpKpzMbNlUGP"

public val IOS_HEADERS: HeadersMap = mapOf(
    HttpHeaders.AcceptLanguage to "zh",
    HttpHeaders.UserAgent to "PixivIOSApp/7.13.3 (iOS 14.6; iPhone13,2)",
    "App-OS-Version" to "14.6",
    "App-OS" to "ios"
)

public const val ANDROID_CLIENT_ID: String = "MOBrBDS8blbauoSck0ZfDbtuzpyT"

public const val ANDROID_CLIENT_SECRET: String = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"

public val ANDROID_HEADERS: HeadersMap = mapOf(
    HttpHeaders.AcceptLanguage to "zh",
    HttpHeaders.UserAgent to "PixivAndroidApp/6.45.0 (Android 8.0)",
    "App-OS-Version" to "8.0",
    "App-OS" to "android"
)

public const val JAPAN_DNS: String = "https://public.dns.iij.jp/dns-query"

public val DEFAULT_PIXIV_HOST: Map<String, List<String>> = mapOf(
    "*.pixiv.net" to listOf("api.fanbox.cc"),
    "sketch.pixiv.net" to listOf("pixivsketch.net"),
    "times.pixiv.net" to listOf("domains.tumblr.com"),
    "matsuri.pixiv.net" to listOf("d37vhba44w9rfk.cloudfront.net"),
    "festa.pixiv.net" to listOf("d27o62ujhz6nk8.cloudfront.net"),
    "iracon.pixiv.net" to listOf("dq5nq916rhniu.cloudfront.net"),
    "g-client-proxy.pixiv.net" to listOf("6837361.gigya-api.com", "d1ctzrip8l97jt.cloudfront.net"),
    "dev.pixiv.net" to listOf("s3-website-ap-northeast-1.amazonaws.com")
)

public const val NO_PROFILE_IMAGE: String = "https://s.pximg.net/common/images/no_profile.png"

public const val LIMIT_UNKNOWN_IMAGE: String = "https://s.pximg.net/common/images/limit_unknown_360.png"

public const val LIMIT_MYPIXIV_IMAGE: String = "https://s.pximg.net/common/images/limit_mypixiv_360.png"

internal fun AuthResult.toBearerTokens(): BearerTokens = BearerTokens(accessToken, refreshToken)

@Suppress("FunctionName")
internal inline fun <reified T : Enum<T>> EnumNameSerializer(): KSerializer<T> {
    return object : KSerializer<T> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor(T::class.qualifiedName!!, PrimitiveKind.STRING)

        override fun serialize(encoder: Encoder, value: T) =
            encoder.encodeString(value.name.lowercase())

        override fun deserialize(decoder: Decoder): T =
            enumValueOf(decoder.decodeString().uppercase())
    }
}

@Suppress("FunctionName")
internal inline fun <reified T : Enum<T>> EnumIndexSerializer(): KSerializer<T> {
    return object : KSerializer<T> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor(T::class.qualifiedName!!, PrimitiveKind.INT)

        override fun serialize(encoder: Encoder, value: T) =
            encoder.encodeInt(value.ordinal)

        override fun deserialize(decoder: Decoder): T {
            val values = enumValues<T>()
            val index = decoder.decodeInt()
            return requireNotNull(values.getOrNull(index)) { "index: $index not in ${values.asList()}" }
        }
    }
}

@Serializable(with = OrderType.NameSerializer::class)
public enum class OrderType {
    DESC,
    ASC;

    override fun toString(): String = name.lowercase()

    public companion object NameSerializer : KSerializer<OrderType> by EnumNameSerializer()

    public object IndexSerializer : KSerializer<OrderType> by EnumIndexSerializer()
}

@Serializable(with = SearchSort.NameSerializer::class)
public enum class SearchSort {
    DATE_DESC,
    DATE_ASC,

    // FOR PREMIUM
    POPULAR_DESC,
    POPULAR_MALE_DESC,
    POPULAR_FEMALE_DESC;

    override fun toString(): String = name.lowercase()

    public companion object NameSerializer : KSerializer<SearchSort> by EnumNameSerializer()

    public object IndexSerializer : KSerializer<SearchSort> by EnumIndexSerializer()
}

@Serializable(with = SearchDuration.NameSerializer::class)
public enum class SearchDuration {
    CUSTOM_DURATION,
    ALL,
    WITHIN_LAST_DAY,
    WITHIN_LAST_WEEK,
    WITHIN_LAST_MONTH,
    WITHIN_HALF_YEAR,
    WITHIN_YEAR,
    SELECT;

    override fun toString(): String = name.lowercase()

    public companion object NameSerializer : KSerializer<SearchDuration> by EnumNameSerializer()

    public object IndexSerializer : KSerializer<SearchDuration> by EnumIndexSerializer()
}

@Serializable(with = PublicityType.NameSerializer::class)
public enum class PublicityType {
    PUBLIC,
    PRIVATE,
    MYPIXIV;

    override fun toString(): String = name.lowercase()

    public companion object NameSerializer : KSerializer<PublicityType> by EnumNameSerializer()

    public object IndexSerializer : KSerializer<PublicityType> by EnumIndexSerializer()
}

@Serializable(with = SearchTarget.NameSerializer::class)
public enum class SearchTarget {
    PARTIAL_MATCH_FOR_TAGS,
    EXACT_MATCH_FOR_TAGS,
    TITLE_AND_CAPTION;

    override fun toString(): String = name.lowercase()

    public companion object NameSerializer : KSerializer<SearchTarget> by EnumNameSerializer()

    public object IndexSerializer : KSerializer<SearchTarget> by EnumIndexSerializer()
}

@Serializable(with = WorkContentType.NameSerializer::class)
public enum class WorkContentType {
    ILLUST,
    UGOIRA,
    MANGA;

    override fun toString(): String = name.lowercase()

    public companion object NameSerializer : KSerializer<WorkContentType> by EnumNameSerializer()

    public object IndexSerializer : KSerializer<WorkContentType> by EnumIndexSerializer()
}

@Serializable(with = RankMode.NameSerializer::class)
public enum class RankMode {
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

    public companion object NameSerializer : KSerializer<RankMode> by EnumNameSerializer()

    public object IndexSerializer : KSerializer<RankMode> by EnumIndexSerializer()
}

@Serializable(with = SanityLevel.IndexSerializer::class)
public enum class SanityLevel {
    UNCHECKED,
    TEMP1,
    WHITE,
    TEMP3,
    SEMI_BLACK,
    TEMP5,
    BLACK,
    NONE;

    override fun toString(): String = name.lowercase()

    public object NameSerializer : KSerializer<SanityLevel> by EnumNameSerializer()

    public companion object IndexSerializer : KSerializer<SanityLevel> by EnumIndexSerializer()
}

@Serializable(with = AgeLimit.IndexSerializer::class)
public enum class AgeLimit {
    ALL {
        override fun toString(): String = "all-age"
    },
    R18 {
        override fun toString(): String = "r18"
    },
    R18G {
        override fun toString(): String = "r18-g"
    };

    public object NameSerializer : KSerializer<AgeLimit> by EnumNameSerializer() {
        override fun deserialize(decoder: Decoder): AgeLimit {
            val value = decoder.decodeString()
            return requireNotNull(values().find { it.toString() == value }) { "$value not in ${values().toList()}" }
        }
    }

    public companion object IndexSerializer : KSerializer<AgeLimit> by EnumIndexSerializer()
}

public enum class FilterType {
    FOR_ANDROID,
    FOR_ISO;

    override fun toString(): String = name.lowercase()
}

@Serializable(with = CategoryType.NameSerializer::class)
public enum class CategoryType {
    ALL,
    ILLUST,
    MANGA;

    override fun toString(): String = name.lowercase()

    public companion object NameSerializer : KSerializer<CategoryType> by EnumNameSerializer()

    public object IndexSerializer : KSerializer<CategoryType> by EnumIndexSerializer()
}

public enum class FollowType {
    SHOW,
    HIDE;

    override fun toString(): String = name.lowercase()
}

public object RegexSerializer : KSerializer<Regex> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlin.text.Regex", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Regex {
        return Regex(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: Regex) {
        encoder.encodeString(value.pattern)
    }
}

public fun Regex.Companion.serializer(): KSerializer<Regex> = RegexSerializer
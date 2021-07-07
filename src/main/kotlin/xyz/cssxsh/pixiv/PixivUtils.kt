package xyz.cssxsh.pixiv

import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import xyz.cssxsh.pixiv.exception.ProxyException
import java.io.IOException
import java.net.*
import java.util.*

typealias HeadersMap = Map<String, String>

typealias FileUrls = Map<String, String>

val PixivJson = Json {
    isLenient = true
    ignoreUnknownKeys = true
    allowSpecialFloatingPointValues = true
    useArrayPolymorphism = false
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

val PIXIV_CNAME = mapOf(
    "oauth.secure.pixiv.net" to "api.fanbox.cc",
    "app-api.pixiv.net" to "api.fanbox.cc",
    "public-api.secure.pixiv.net" to "api.fanbox.cc",
    "public.pixiv.net" to "api.fanbox.cc",
    "www.pixiv.net" to "api.fanbox.cc",
    "pixiv.me" to "api.fanbox.cc",
    "accounts.pixiv.net" to "api.fanbox.cc",
    "g-client-proxy.pixiv.net" to "6837361.gigya-api.com"
)

internal fun Url.toProxy(): Proxy {
    val type = when (protocol) {
        URLProtocol.SOCKS -> Proxy.Type.SOCKS
        URLProtocol.HTTP -> Proxy.Type.HTTP
        else -> throw ProxyException(this)
    }
    return Proxy(type, InetSocketAddress(host, port))
}

internal fun ProxySelector(proxy: String, cname: Map<String, String>) = object : ProxySelector() {
    override fun select(uri: URI?): MutableList<Proxy> = mutableListOf<Proxy>().apply {
        if (uri?.host !in cname) add(Url(proxy).toProxy())
    }

    override fun connectFailed(uri: URI?, sa: SocketAddress?, ioe: IOException?) {
        // println("connectFailed； $uri")
    }
}

interface PixivParam {
    val name: String
    fun value() = name.lowercase()
}

@Suppress("FunctionName")
inline fun <reified T> PixivEnumSerializer(): KSerializer<T> where T : PixivParam, T : Enum<T> {
    return object : KSerializer<T> {
        override val descriptor: SerialDescriptor =
            buildSerialDescriptor(T::class.qualifiedName!!, SerialKind.ENUM)

        override fun serialize(encoder: Encoder, value: T) =
            encoder.encodeString(value.value())

        override fun deserialize(decoder: Decoder): T =
            enumValueOf(decoder.decodeString().uppercase())
    }
}

@Suppress("FunctionName")
inline fun <reified T> PixivTypeSerializer(): KSerializer<T> where T : PixivParam, T : Enum<T> {
    return object : KSerializer<T> {
        override val descriptor: SerialDescriptor =
            buildSerialDescriptor(T::class.qualifiedName!!, PrimitiveKind.INT)

        override fun serialize(encoder: Encoder, value: T) =
            encoder.encodeInt(value.ordinal)

        override fun deserialize(decoder: Decoder): T =
            requireNotNull(enumValues<T>().getOrNull(decoder.decodeInt())) { "index: ${decoder.decodeInt()} not in ${enumValues<T>()}" }
    }
}

@Serializable(with = SearchMode.Companion::class)
enum class SearchMode : PixivParam {
    TEXT,
    CAPTION,
    TAG,
    EXACT_TAG;

    companion object : KSerializer<SearchMode> by PixivEnumSerializer()
}

@Serializable(with = OrderType.Companion::class)
enum class OrderType : PixivParam {
    DESC,
    ASC;

    companion object : KSerializer<OrderType> by PixivEnumSerializer()
}

@Serializable(with = PeriodType.Companion::class)
enum class PeriodType : PixivParam {
    ALL,
    DAY,
    WEEK,
    MONTH;

    companion object : KSerializer<PeriodType> by PixivEnumSerializer()
}

@Serializable(with = SortMode.Companion::class)
enum class SortMode : PixivParam {
    DATE,
    POPULAR;

    companion object : KSerializer<SortMode> by PixivEnumSerializer()
}

@Serializable(with = SearchSort.Companion::class)
enum class SearchSort : PixivParam {
    DATE_DESC,
    DATE_ASC,

    // 高级会员选项
    POPULAR_DESC,
    POPULAR_MALE_DESC,
    POPULAR_FEMALE_DESC;

    companion object : KSerializer<SearchSort> by PixivEnumSerializer()
}

@Serializable(with = SearchDuration.Companion::class)
enum class SearchDuration : PixivParam {
    CUSTOM_DURATION,
    ALL,
    WITHIN_LAST_DAY,
    WITHIN_LAST_WEEK,
    WITHIN_LAST_MONTH,
    WITHIN_HALF_YEAR,
    WITHIN_YEAR,
    SELECT;

    companion object : KSerializer<SearchDuration> by PixivEnumSerializer()
}

enum class SearchSize : PixivParam {
    MINIMUM,
    MEDIUM,
    LARGE;
}

enum class SearchIllustTool : PixivParam {
    PHOTOSHOP,
    ILLUSTRATOR,
    FIREWORKS;
}

enum class SearchAspectRatio : PixivParam {
    PORTRAIT,
    LANDSCAPE,
    SQUARE;
}

@Serializable(with = PublicityType.Companion::class)
enum class PublicityType : PixivParam {
    PUBLIC,
    PRIVATE,
    TEMP;

    companion object : KSerializer<PublicityType> by PixivEnumSerializer() {
        object TypeSerializer : KSerializer<PublicityType> by PixivTypeSerializer()
    }
}

@Serializable(with = SearchTarget.Companion::class)
enum class SearchTarget : PixivParam {
    PARTIAL_MATCH_FOR_TAGS,
    EXACT_MATCH_FOR_TAGS,
    TITLE_AND_CAPTION,
    TEST,
    KEYWORD;

    companion object : KSerializer<SearchTarget> by PixivEnumSerializer()
}

@Serializable(with = WorkContentType.Companion::class)
enum class WorkContentType : PixivParam {
    ILLUST,
    UGOIRA,
    MANGA;

    companion object : KSerializer<WorkContentType> by PixivEnumSerializer()
}

@Serializable(with = WorkType.Companion::class)
enum class WorkType : PixivParam {
    ILLUST,
    MANGA;

    companion object : KSerializer<WorkType> by PixivEnumSerializer()
}

@Serializable(with = RankMode.Companion::class)
enum class RankMode : PixivParam {
    // MONTH
    MONTH,

    // WEEK
    WEEK,
    WEEK_ORIGINAL,
    WEEK_ROOKIE,

    // DAY
    DAY,
    DAY_MALE,
    DAY_FEMALE,
    DAY_MANGA,

    // R18
    DAY_R18,
    DAY_MALE_R18,
    DAY_FEMALE_R18,
    WEEK_R18,
    WEEK_R18G;

    companion object : KSerializer<RankMode> by PixivEnumSerializer()
}

@Serializable(with = SanityLevel.Companion::class)
enum class SanityLevel : PixivParam {
    UNCHECKED,
    TEMP1,
    WHITE,
    TEMP3,
    SEMI_BLACK,
    TEMP5,
    BLACK,
    NONE;

    companion object : KSerializer<SanityLevel> by PixivEnumSerializer() {
        object TypeSerializer : KSerializer<SanityLevel> by PixivTypeSerializer()
    }
}

@Serializable(with = AgeLimit.Companion::class)
enum class AgeLimit : PixivParam {
    ALL {
        override fun value() = "all-age"
    },
    R18 {
        override fun value() = "r18"
    },
    R18G {
        override fun value() = "r18-g"
    };

    companion object : KSerializer<AgeLimit> by PixivEnumSerializer() {
        override fun deserialize(decoder: Decoder): AgeLimit = decoder.decodeString().let { value ->
            requireNotNull(values().find { it.value() == value }) { "$value not in ${values().toList()}" }
        }

        object TypeSerializer : KSerializer<AgeLimit> by PixivTypeSerializer()
    }
}

enum class FilterType : PixivParam {
    FOR_ANDROID,
    FOR_ISO;
}

@Serializable(with = CategoryType.Companion::class)
enum class CategoryType : PixivParam {
    ALL,
    ILLUST,
    MANGA;

    companion object : KSerializer<SanityLevel> by PixivEnumSerializer()
}
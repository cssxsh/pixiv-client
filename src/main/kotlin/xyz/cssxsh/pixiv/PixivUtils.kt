@file:Suppress("unused")

package xyz.cssxsh.pixiv

import io.ktor.client.engine.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.encoding.Encoder
import xyz.cssxsh.pixiv.client.exception.ProxyException
import java.net.InetSocketAddress
import java.net.Proxy
import java.net.URL
import kotlin.reflect.KClass

typealias HeadersMap = Map<String, String>

typealias FileUrls = Map<String, String>

const val CLIENT_ID: String = "MOBrBDS8blbauoSck0ZfDbtuzpyT"

const val CLIENT_SECRET: String = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"

const val HASH_SECRET: String = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c"

val IOS_HEADERS: HeadersMap = mapOf(
    "Accept-Language" to "zh_CN",
    "User-Agent" to "PixivIOSApp/6.0.4 (iOS 9.0.2; iPhone6,1)",
    "App-OS-Version" to "9.0.2",
    "App-OS" to "ios",
    "App-Version" to "6.0.4"
)

val ANDROID_HEADERS: HeadersMap = mapOf(
    "Accept-Language" to "zh_CN",
    "User-Agent" to "PixivAndroidApp/5.0.64 (Android 6.0)",
    "App-OS-Version" to "6.0",
    "App-OS" to "android",
    "App-Version" to "5.0.64"
)

const val JAPAN_DNS: String = "https://public.dns.iij.jp/dns-query"

internal fun String.toProxy(): ProxyConfig = URL(this).let { proxy ->
    when (proxy.protocol) {
        "http" -> Proxy(Proxy.Type.HTTP, InetSocketAddress(proxy.host, proxy.port))
        "socks", "socks4", "socks5" -> Proxy(Proxy.Type.SOCKS, InetSocketAddress(proxy.host, proxy.port))
        else -> throw ProxyException(this)
    }
}

interface PixivParam {
    val name: String
    fun value() = name.toLowerCase()
}

open class PixivEnumSerializer<T>(
    with: KClass<T>,
    private val valueOf: (name: String) -> T,
) : KSerializer<T> where T : PixivParam, T : Enum<T> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(with.qualifiedName!!, SerialKind.ENUM)

    override fun serialize(encoder: Encoder, value: T) =
        encoder.encodeString(value.value())

    override fun deserialize(decoder: Decoder): T =
        valueOf(decoder.decodeString().toUpperCase())
}

open class PixivTypeSerializer<T>(
    val with: KClass<T>,
    private val values: () -> Array<T>,
) : KSerializer<T> where T : PixivParam, T : Enum<T> {

    override val descriptor: SerialDescriptor = buildSerialDescriptor(with.qualifiedName!!, SerialKind.ENUM)

    override fun serialize(encoder: Encoder, value: T) =
        encoder.encodeInt(value.ordinal)

    override fun deserialize(decoder: Decoder): T =
        requireNotNull(values().getOrNull(decoder.decodeInt())) { "index: ${decoder.decodeInt()} not in $with" }
}

enum class GrantType : PixivParam {
    PASSWORD,
    REFRESH_TOKEN;
}

@Serializable(with = SearchMode.Companion::class)
enum class SearchMode : PixivParam {
    TEXT,
    CAPTION,
    TAG,
    EXACT_TAG;

    companion object : PixivEnumSerializer<SearchMode>(
        with = SearchMode::class,
        valueOf = ::enumValueOf
    )
}

@Serializable(with = OrderType.Companion::class)
enum class OrderType : PixivParam {
    DESC,
    ASC;

    companion object : PixivEnumSerializer<OrderType>(
        with = OrderType::class,
        valueOf = ::enumValueOf
    )
}

@Serializable(with = PeriodType.Companion::class)
enum class PeriodType : PixivParam {
    ALL,
    DAY,
    WEEK,
    MONTH;

    @Serializer(forClass = PeriodType::class)
    companion object : PixivEnumSerializer<PeriodType>(
        with = PeriodType::class,
        valueOf = ::enumValueOf
    )
}

@Serializable(with = SortMode.Companion::class)
enum class SortMode : PixivParam {
    DATE,
    POPULAR;

    companion object : PixivEnumSerializer<SortMode>(
        with = SortMode::class,
        valueOf = ::enumValueOf
    )
}

@Serializable(with = SortType.Companion::class)
enum class SortType : PixivParam {
    DATE_DESC,
    DATE_ASC;

    companion object : PixivEnumSerializer<SortType>(
        with = SortType::class,
        valueOf = ::enumValueOf
    )
}

@Serializable(with = DurationType.Companion::class)
enum class DurationType : PixivParam {
    WITHIN_LAST_DAY,
    WITHIN_LAST_WEEK,
    WITHIN_LAST_MONTH;

    companion object : PixivEnumSerializer<DurationType>(
        with = DurationType::class,
        valueOf = ::enumValueOf
    )
}

@Serializable(with = PublicityType.Companion::class)
enum class PublicityType : PixivParam {
    PUBLIC,
    PRIVATE;

    companion object : PixivEnumSerializer<PublicityType>(
        with = PublicityType::class,
        valueOf = ::enumValueOf
    ) {
        object TypeSerializer : PixivTypeSerializer<PublicityType>(
            with = PublicityType::class,
            values = ::enumValues
        )
    }
}

@Serializable(with = SearchTarget.Companion::class)
enum class SearchTarget : PixivParam {
    PARTIAL_MATCH_FOR_TAGS,
    EXACT_MATCH_FOR_TAGS,
    TITLE_AND_CAPTION;

    companion object : PixivEnumSerializer<SearchTarget>(
        with = SearchTarget::class,
        valueOf = ::enumValueOf
    )
}

@Serializable(with = WorkContentType.Companion::class)
enum class WorkContentType : PixivParam {
    ILLUST,
    UGOIRA,
    MANGA;

    companion object : PixivEnumSerializer<WorkContentType>(
        with = WorkContentType::class,
        valueOf = ::enumValueOf
    )
}

@Serializable(with = WorkContentType.Companion::class)
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

    companion object : PixivEnumSerializer<RankMode>(
        with = RankMode::class,
        valueOf = ::enumValueOf
    )
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
    TEMP7;

    companion object : PixivEnumSerializer<SanityLevel>(
        with = SanityLevel::class,
        valueOf = ::enumValueOf
    ) {
        object TypeSerializer : PixivTypeSerializer<SanityLevel>(
            with = SanityLevel::class,
            values = ::enumValues
        )
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
        override fun value() = "r18g"
    };

    companion object : PixivEnumSerializer<AgeLimit>(
        with = AgeLimit::class,
        valueOf = ::enumValueOf
    ) {
        override fun deserialize(decoder: Decoder): AgeLimit =
            valueOf(decoder.decodeString().replace("""-age""".toRegex(), "").toUpperCase())
    }
}
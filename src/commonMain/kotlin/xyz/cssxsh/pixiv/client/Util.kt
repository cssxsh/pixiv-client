@file:Suppress("unused")
package xyz.cssxsh.pixiv.client

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias ParamsMap = Map<String, Any?>

typealias HeadersMap = Map<String, String>

object Util {
    const val CLIENT_ID: String = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
    const val  CLIENT_SECRET: String = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"
    const val  HASH_SECRET: String = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c"
    const val  OAUTH_URL: String = "https://oauth.secure.pixiv.net/auth/token"
    val  DEFAULT_HEADERS_MAP: HeadersMap = mapOf(
        "User-Agent" to "PixivAndroidApp/5.0.64 (Android 6.0)",
        "Accept-Language" to "jp"
    )
}

interface ParamEnum {
    val name: String
    fun value() = name.toLowerCase()
}

enum class GrantType: ParamEnum {
    PASSWORD,
    REFRESH_TOKEN;
}

enum class SearchMode: ParamEnum {
    TEXT,
    CAPTION,
    TAG,
    EXACT_TAG;
}

enum class OrderType: ParamEnum {
    DESC,
    ASC;
}

enum class PeriodType: ParamEnum {
    ALL,
    DAY,
    WEEK,
    MONTH;
}

enum class SortMode: ParamEnum {
    DATE,
    POPULAR;
}

enum class SortType: ParamEnum {
    DATE_DESC,
    DATE_ASC;
}

enum class DurationType: ParamEnum {
    WITHIN_LAST_DAY,
    WITHIN_LAST_WEEK,
    WITHIN_LAST_MONTH;
}

@Serializable
enum class PublicityType: ParamEnum {
    PUBLIC,
    PRIVATE;

    @Serializer(forClass = PublicityType::class)
    companion object : KSerializer<PublicityType> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("${PublicityType::class}ToStringSerializer", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): PublicityType = valueOf(decoder.decodeString().toUpperCase())

        override fun serialize(encoder: Encoder, value: PublicityType) = encoder.encodeString(value.value())
    }
}

enum class SearchTarget: ParamEnum {
    PARTIAL_MATCH_FOR_TAGS,
    EXACT_MATCH_FOR_TAGS,
    TITLE_AND_CAPTION;
}

enum class WorkType: ParamEnum {
    ILLUST,
    NOVEL;
}

@Serializable
enum class ContentType: ParamEnum {
    ILLUST,
    UGOIRA,
    MANGA;

    @Serializer(forClass = ContentType::class)
    companion object : KSerializer<ContentType> {
        override val descriptor: SerialDescriptor
            get() = PrimitiveSerialDescriptor("${ContentType::class}ToStringSerializer", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): ContentType = valueOf(decoder.decodeString().toUpperCase())

        override fun serialize(encoder: Encoder, value: ContentType) = encoder.encodeString(value.value())
    }
}

enum class Mode: ParamEnum {

}

enum class Method {
    GET,
    POST,
    DELETE;
}
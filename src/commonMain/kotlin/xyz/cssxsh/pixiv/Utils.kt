@file:Suppress("unused")

package xyz.cssxsh.pixiv

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias ParamsMap = Map<String, Any?>

typealias HeadersMap = Map<String, String>

typealias FileUrls = Map<String, String>


inline fun <reified T> paramEnumOf(value: String): T where T : Enum<T>, T : ParamEnum = enumValueOf(value.toUpperCase())

interface ParamEnum {
    val name: String
    fun value() = name.toLowerCase()
}

interface ParamEnumSerializer<T : ParamEnum> : KSerializer<T> {

    val serialName: String

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(serialName, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: T) =
        encoder.encodeString(value.value())
}

enum class GrantType : ParamEnum {
    PASSWORD,
    REFRESH_TOKEN;
}

enum class SearchMode : ParamEnum {
    TEXT,
    CAPTION,
    TAG,
    EXACT_TAG;
}

enum class OrderType : ParamEnum {
    DESC,
    ASC;
}

enum class PeriodType : ParamEnum {
    ALL,
    DAY,
    WEEK,
    MONTH;
}

enum class SortMode : ParamEnum {
    DATE,
    POPULAR;
}

enum class SortType : ParamEnum {
    DATE_DESC,
    DATE_ASC;
}

enum class DurationType : ParamEnum {
    WITHIN_LAST_DAY,
    WITHIN_LAST_WEEK,
    WITHIN_LAST_MONTH;
}

@Serializable
enum class PublicityType : ParamEnum {
    PUBLIC,
    PRIVATE;

    @Serializer(forClass = PublicityType::class)
    companion object : ParamEnumSerializer<PublicityType>, KSerializer<PublicityType> {

        override val serialName: String = "ParamEnumSerializer<PublicityType>"

        override fun deserialize(decoder: Decoder): PublicityType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

enum class SearchTarget : ParamEnum {
    PARTIAL_MATCH_FOR_TAGS,
    EXACT_MATCH_FOR_TAGS,
    TITLE_AND_CAPTION;
}

enum class WorkType : ParamEnum {
    ILLUST,
    NOVEL;
}

@Serializable
enum class ContentType : ParamEnum {
    ILLUST,
    UGOIRA,
    MANGA;

    @Serializer(forClass = ContentType::class)
    companion object : ParamEnumSerializer<ContentType>, KSerializer<ContentType> {
        override val serialName: String = "ParamEnumSerializer<ContentType>"

        override fun deserialize(decoder: Decoder): ContentType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

enum class RankMode : ParamEnum {
    DAY,
    DAY_MALE,
    DAY_FEMALE,
    WEEK_ORIGINAL,
    WEEK_ROOKIE,
    WEEK,
    MONTH,
    DAY_R18,
    DAY_MALE_R18,
    DAY_FEMALE_R18,
    WEEK_R18,
    WEEK_R18G,
    DAY_MANGA;
}

enum class Method {
    GET,
    POST,
    DELETE;
}

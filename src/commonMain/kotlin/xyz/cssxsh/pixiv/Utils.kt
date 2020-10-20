@file:Suppress("unused")

package xyz.cssxsh.pixiv

import io.ktor.client.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import xyz.cssxsh.pixiv.client.PixivClient

suspend inline fun <reified R> PixivClient.useHttpClient(
    crossinline block: suspend PixivClient.(HttpClient) -> R
) = httpClient().use {
    block(it)
}

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

@Serializable
enum class GrantType : ParamEnum {
    PASSWORD,
    REFRESH_TOKEN;

    @Serializer(forClass = GrantType::class)
    companion object : ParamEnumSerializer<GrantType>, KSerializer<GrantType> {

        override val serialName: String = "ParamEnumSerializer<${GrantType::class}>"

        override fun deserialize(decoder: Decoder): GrantType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class SearchMode : ParamEnum {
    TEXT,
    CAPTION,
    TAG,
    EXACT_TAG;

    @Serializer(forClass = SearchMode::class)
    companion object : ParamEnumSerializer<SearchMode>, KSerializer<SearchMode> {

        override val serialName: String = "ParamEnumSerializer<${SearchMode::class}>"

        override fun deserialize(decoder: Decoder): SearchMode =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class OrderType : ParamEnum {
    DESC,
    ASC;

    @Serializer(forClass = OrderType::class)
    companion object : ParamEnumSerializer<OrderType>, KSerializer<OrderType> {

        override val serialName: String = "ParamEnumSerializer<${OrderType::class}>"

        override fun deserialize(decoder: Decoder): OrderType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class PeriodType : ParamEnum {
    ALL,
    DAY,
    WEEK,
    MONTH;

    @Serializer(forClass = PeriodType::class)
    companion object : ParamEnumSerializer<PeriodType>, KSerializer<PeriodType> {

        override val serialName: String = "ParamEnumSerializer<${PeriodType::class}>"

        override fun deserialize(decoder: Decoder): PeriodType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class SortMode : ParamEnum {
    DATE,
    POPULAR;

    @Serializer(forClass = SortMode::class)
    companion object : ParamEnumSerializer<SortMode>, KSerializer<SortMode> {

        override val serialName: String = "ParamEnumSerializer<${SortMode::class}>"

        override fun deserialize(decoder: Decoder): SortMode =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class SortType : ParamEnum {
    DATE_DESC,
    DATE_ASC;

    @Serializer(forClass = SortType::class)
    companion object : ParamEnumSerializer<SortType>, KSerializer<SortType> {

        override val serialName: String = "ParamEnumSerializer<${SortMode::class}>"

        override fun deserialize(decoder: Decoder): SortType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class DurationType : ParamEnum {
    WITHIN_LAST_DAY,
    WITHIN_LAST_WEEK,
    WITHIN_LAST_MONTH;

    @Serializer(forClass = DurationType::class)
    companion object : ParamEnumSerializer<DurationType>, KSerializer<DurationType> {

        override val serialName: String = "ParamEnumSerializer<${DurationType::class}>"

        override fun deserialize(decoder: Decoder): DurationType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class PublicityType : ParamEnum {
    PUBLIC,
    PRIVATE;

    @Serializer(forClass = PublicityType::class)
    companion object : ParamEnumSerializer<PublicityType>, KSerializer<PublicityType> {

        override val serialName: String = "ParamEnumSerializer<${PublicityType::class}>"

        override fun deserialize(decoder: Decoder): PublicityType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class SearchTarget : ParamEnum {
    PARTIAL_MATCH_FOR_TAGS,
    EXACT_MATCH_FOR_TAGS,
    TITLE_AND_CAPTION;

    @Serializer(forClass = SearchTarget::class)
    companion object : ParamEnumSerializer<SearchTarget>, KSerializer<SearchTarget> {

        override val serialName: String = "ParamEnumSerializer<${SearchTarget::class}>"

        override fun deserialize(decoder: Decoder): SearchTarget =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class WorkType : ParamEnum {
    ILLUST,
    NOVEL;

    @Serializer(forClass = WorkType::class)
    companion object : ParamEnumSerializer<WorkType>, KSerializer<WorkType> {
        override val serialName: String = "ParamEnumSerializer<${WorkType::class}>"

        override fun deserialize(decoder: Decoder): WorkType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
enum class WorkContentType : ParamEnum {
    ILLUST,
    UGOIRA,
    MANGA;

    @Serializer(forClass = WorkContentType::class)
    companion object : ParamEnumSerializer<WorkContentType>, KSerializer<WorkContentType> {
        override val serialName: String = "ParamEnumSerializer<${WorkContentType::class}>"

        override fun deserialize(decoder: Decoder): WorkContentType =
            valueOf(decoder.decodeString().toUpperCase())
    }
}

@Serializable
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

    @Serializer(forClass = RankMode::class)
    companion object : ParamEnumSerializer<RankMode>, KSerializer<RankMode> {
        override val serialName: String = "ParamEnumSerializer<${RankMode::class}>"

        override fun deserialize(decoder: Decoder): RankMode =
            valueOf(decoder.decodeString().toUpperCase())
    }
}
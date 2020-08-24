package xyz.cssxsh.pixiv.client

import kotlinx.serialization.*

typealias ParamsMap = Map<String, Any?>

typealias HeadersMap = Map<String, String>

object Util {
    var CLIENT_ID: String = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
    var CLIENT_SECRET: String = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"
    var HASH_SECRET: String = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c"
    var OAUTH_URL: String = "https://oauth.secure.pixiv.net/auth/token"
    var DEFAULT_HEADERS_MAP: HeadersMap = mutableMapOf(
        "User-Agent" to "PixivAndroidApp/5.0.64 (Android 6.0)",
        "Accept-Language" to "jp"
    )
}

enum class GrantType(val text: String) {
    PASSWORD("password"),
    REFRESH("refresh_token");

    override fun toString(): String = text

    companion object {
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()
    }
}

enum class SearchMode(val text: String) {
    TEXT("text"),
    CAPTION("caption"),
    TAG("tag"),
    EXACT_TAG("exact_tag");

    override fun toString(): String = text

    companion object{
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()
    }
}

enum class OrderType(val text: String) {
    DESC("desc"),
    ASC("asc");

    override fun toString(): String = text

    companion object{
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()
    }
}

enum class PeriodType(val text: String) {
    ALL("all"),
    DAY("day"),
    WEEK("week"),
    MONTH("month");

    override fun toString(): String = text

    companion object {
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()
    }
}

enum class SortMode(val text: String) {
    DATE("date"),
    POPULAR("popular");

    override fun toString(): String = text

    companion object {
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()
    }
}

enum class SortType(val text: String) {
    DATE_DESC("date_asc"),
    DATE_ASC("date_asc");

    override fun toString(): String = text

    companion object {
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()
    }
}

enum class DurationType(val text: String) {
    WITHIN_LAST_DAY("within_last_day"),
    WITHIN_LAST_WEEK("within_last_week"),
    WITHIN_LAST_MONTH("within_last_month");

    override fun toString(): String = text

    companion object {
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()
    }
}

@Serializable
enum class PublicityType(val text: String) {
    PUBLIC("public"),
    PRIVATE("private");

    override fun toString(): String = text

    @Serializer(forClass = PublicityType::class)
    companion object : KSerializer<PublicityType> {
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()

        /*
        override val descriptor: SerialDescriptor
            get() = PrimitiveDescriptor("${PublicityType::class}ToStringSerializer", PrimitiveKind.STRING)

        override fun deserialize(decoder: Decoder): PublicityType = try {
            values()[decoder.decodeInt()]
        } catch (e: Exception) {
            parse(decoder.decodeString())
        }


        override fun serialize(encoder: Encoder, value: PublicityType) = encoder.encodeString(value.text)
         */
    }
}

enum class SearchTarget(val text: String) {
    PARTIAL_MATCH_FOR_TAGS("partial_match_for_tags"),
    EXACT_MATCH_FOR_TAGS("exact_match_for_tags"),
    TITLE_AND_CAPTION("title_and_caption");

    override fun toString(): String = text

    companion object {
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()
    }
}

enum class Method(val text: String) {
    GET("GET"),
    POST("POST"),
    DELETE("DELETE");

    override fun toString(): String = text

    companion object {
        fun parse(text: String) = values().find { it.text == text } ?: throw IllegalArgumentException()
    }
}
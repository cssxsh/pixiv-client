package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*
import kotlinx.serialization.builtins.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*

@Serializable
class WebApiResult(
    @SerialName("body")
    val body: JsonElement,
    @SerialName("error")
    val error: Boolean,
    @SerialName("message")
    val message: String,
)

inline fun <reified T> WebApiResult.value(): T {
    check(error.not()) { message }
    return Json.decodeFromJsonElement(body)
}

object WepApiSet : KSerializer<Set<Long>> {
    private val serializer get() = SetSerializer(Long.serializer())

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun deserialize(decoder: Decoder): Set<Long> {
        return decoder.decodeSerializableValue(JsonElement.serializer()).let { element ->
            if (element is JsonObject) element.mapTo(mutableSetOf()) { it.key.toLong() } else emptySet()
        }
    }

    override fun serialize(encoder: Encoder, value: Set<Long>) {
        if (value.isEmpty()) {
            encoder.encodeSerializableValue(serializer, value)
        } else {
            encoder.encodeSerializableValue(JsonObject.serializer(), buildJsonObject {
                value.forEach { put(it.toString(), JsonNull) }
            })
        }
    }

}

sealed class WebTemp<T> {
    private val origin: JsonElement = JsonNull

    abstract val serializer: KSerializer<T>

    val value: T? get() = if (origin is JsonNull) null else Json.decodeFromJsonElement(serializer, origin)

    @Serializable
    class Bookmark(override val serializer: KSerializer<BookmarkData> = BookmarkData.serializer()) :
        WebTemp<BookmarkData>()
}
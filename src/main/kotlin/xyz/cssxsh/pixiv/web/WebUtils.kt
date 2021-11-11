package xyz.cssxsh.pixiv.web

import io.ktor.client.request.*
import kotlinx.serialization.*
import kotlinx.serialization.builtins.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.exception.*

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
    if (error) throw WebApiException(this)
    return PixivJson.decodeFromJsonElement(body)
}

suspend inline fun <reified T> UseHttpClient.web(
    api: String,
    crossinline block: HttpRequestBuilder.() -> Unit = {}
): T = useHttpClient { client ->
    client.request<WebApiResult>(api, block).value()
}

object WepApiSet : KSerializer<Set<Long>> {
    private val serializer = SetSerializer(Long.serializer())

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun deserialize(decoder: Decoder): Set<Long> {
        val element = decoder.decodeSerializableValue(JsonElement.serializer())
        return if (element is JsonObject) element.mapTo(mutableSetOf()) { it.key.toLong() } else emptySet()
    }

    override fun serialize(encoder: Encoder, value: Set<Long>) {
        if (value.isNotEmpty()) {
            encoder.encodeSerializableValue(JsonObject.serializer(), buildJsonObject {
                for (item in value) {
                    put(item.toString(), JsonNull)
                }
            })
        } else {
            encoder.encodeSerializableValue(serializer, emptySet())
        }
    }
}

sealed class WebApiMap<V: WebWorkInfo>(valueSerializer: KSerializer<V>) : KSerializer<Map<Long, V>> {
    private val serializer: KSerializer<Map<Long, V>> = MapSerializer(Long.serializer(), valueSerializer)

    override val descriptor: SerialDescriptor get() = serializer.descriptor

    override fun deserialize(decoder: Decoder): Map<Long, V> {
        val element = decoder.decodeSerializableValue(JsonElement.serializer())
        return if (element is JsonObject) {
            decoder.decodeSerializableValue(serializer)
        } else {
            emptyMap()
        }
    }

    override fun serialize(encoder: Encoder, value: Map<Long, V>) {
        if (value.isNotEmpty()) {
            encoder.encodeSerializableValue(serializer, value)
        } else {
            encoder.encodeSerializableValue(ListSerializer(Unit.serializer()), emptyList())
        }
    }


    object Illust: WebApiMap<WebIllust>(WebIllust.serializer())

    object Novel: WebApiMap<WebNovel>(WebNovel.serializer())
}

// https://www.pixiv.net/ajax/tags/frequent/illust?lang=zh
package xyz.cssxsh.pixiv

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.time.format.*
import java.time.*

interface OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {

    val dateFormat: DateTimeFormatter

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("OffsetDateTimeSerializerTo[${dateFormat}]", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime): Unit =
        encoder.encodeString(value.format(dateFormat))

    override fun deserialize(decoder: Decoder): OffsetDateTime =
        OffsetDateTime.parse(decoder.decodeString(), dateFormat)
}
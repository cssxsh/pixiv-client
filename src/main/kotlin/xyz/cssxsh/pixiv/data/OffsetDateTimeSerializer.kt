package xyz.cssxsh.pixiv.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.format.DateTimeFormatter
import java.time.OffsetDateTime

interface OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {

    val dateFormat: DateTimeFormatter

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("OffsetDateTimeSerializerTo[${dateFormat}]", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime): Unit =
        encoder.encodeString(value.format(dateFormat))

    override fun deserialize(decoder: Decoder): OffsetDateTime =
        OffsetDateTime.parse(decoder.decodeString(), dateFormat)
}
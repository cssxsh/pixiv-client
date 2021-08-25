package xyz.cssxsh.pixiv

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.time.*
import java.time.format.*

interface LocalDateSerializer : KSerializer<LocalDate> {

    val dateFormat: DateTimeFormatter

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("LocalDateSerializerTo[${dateFormat}]", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate): Unit = encoder.encodeString(value.format(dateFormat))

    override fun deserialize(decoder: Decoder): LocalDate = LocalDate.parse(decoder.decodeString(), dateFormat)
}
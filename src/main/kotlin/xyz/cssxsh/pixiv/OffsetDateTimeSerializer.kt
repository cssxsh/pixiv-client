package xyz.cssxsh.pixiv

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import java.time.*
import java.time.format.*

public interface OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {

    public val formatter: DateTimeFormatter

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor(OffsetDateTime::class.qualifiedName!!, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime): Unit =
        encoder.encodeString(value.format(formatter))

    override fun deserialize(decoder: Decoder): OffsetDateTime =
        OffsetDateTime.parse(decoder.decodeString(), formatter)
}
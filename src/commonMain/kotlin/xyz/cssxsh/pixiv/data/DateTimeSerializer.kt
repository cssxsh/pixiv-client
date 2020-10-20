package xyz.cssxsh.pixiv.data

import com.soywiz.klock.wrapped.WDateTimeTz
import com.soywiz.klock.PatternDateFormat
import com.soywiz.klock.parse
import com.soywiz.klock.wrapped.wrapped
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

open class DateTimeSerializer(
    val dateFormat: PatternDateFormat
): KSerializer<WDateTimeTz> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("DateTimeSerializerTo${dateFormat}", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: WDateTimeTz): Unit = encoder.encodeString(value.format(dateFormat))

    override fun deserialize(decoder: Decoder): WDateTimeTz = dateFormat.parse(decoder.decodeString()).wrapped
}
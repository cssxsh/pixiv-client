package xyz.cssxsh.pixiv.data

import com.soywiz.klock.PatternDateFormat
import com.soywiz.klock.parseDate
import com.soywiz.klock.wrapped.WDate
import com.soywiz.klock.wrapped.wrapped
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class DateSerializer(
    var dateFormat: PatternDateFormat
): KSerializer<WDate> {
    // "yyyy-MM-dd" PatternDateFormat

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("DateSerializerTo${dateFormat}", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: WDate) = encoder.encodeString(value.format(dateFormat))

    override fun deserialize(decoder: Decoder): WDate = dateFormat.parseDate(decoder.decodeString()).wrapped
}
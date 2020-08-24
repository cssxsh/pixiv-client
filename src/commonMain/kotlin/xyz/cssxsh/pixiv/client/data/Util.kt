package xyz.cssxsh.pixiv.client.data


import kotlinx.serialization.Serializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import com.soywiz.klock.DateTimeTz
import com.soywiz.klock.PatternDateFormat
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.parse
import com.soywiz.klock.locale.japanese
import com.soywiz.klock.wrapped.WDate
import com.soywiz.klock.wrapped.wrapped

typealias FileUrls = Map<String, String>

typealias Tags = List<String>

@Serializer(forClass = DateTimeTz::class)
internal object DateTimeSerializer: KSerializer<DateTimeTz> {
    // "yyyy-MM-dd HH:mm:ss" PatternDateFormat
    private val dateFormat: PatternDateFormat = PatternDateFormat("yyyy-MM-dd HH:mm:ss", KlockLocale.japanese)

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("DateTimeSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DateTimeTz): Unit = encoder.encodeString(value.format(dateFormat))

    override fun deserialize(decoder: Decoder): DateTimeTz = dateFormat.parse(decoder.decodeString())
}


@Serializer(forClass = WDate::class)
internal object DateSerializer: KSerializer<WDate> {
    // "yyyy-MM-dd" PatternDateFormat
    private val dateFormat: PatternDateFormat = PatternDateFormat("yyyy-MM-dd", KlockLocale.japanese)

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("DateSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: WDate) = encoder.encodeString(value.format(dateFormat))

    override fun deserialize(decoder: Decoder): WDate = dateFormat.parse(decoder.decodeString()).local.wrapped.date
}

package xyz.cssxsh.pixiv

import kotlinx.serialization.encoding.Decoder
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

object JapanLocalDateTimeSerializer: OffsetDateTimeSerializer {
    override val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.JAPAN)

    override fun deserialize(decoder: Decoder): OffsetDateTime =
        OffsetDateTime.of(LocalDateTime.parse(decoder.decodeString(), dateFormat), ZoneOffset.ofHours(9))
}
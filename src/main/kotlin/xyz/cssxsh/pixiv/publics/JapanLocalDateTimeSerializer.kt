package xyz.cssxsh.pixiv.publics

import kotlinx.serialization.encoding.*
import xyz.cssxsh.pixiv.*
import java.time.*
import java.time.format.*
import java.util.*

object JapanLocalDateTimeSerializer: OffsetDateTimeSerializer {
    override val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.JAPAN)

    override fun deserialize(decoder: Decoder): OffsetDateTime =
        OffsetDateTime.of(LocalDateTime.parse(decoder.decodeString(), dateFormat), ZoneOffset.ofHours(9))
}
package xyz.cssxsh.pixiv

import java.time.format.DateTimeFormatter
import java.util.*

object JapanDateTimeSerializer : OffsetDateTimeSerializer {
    override val dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.JAPAN)
}
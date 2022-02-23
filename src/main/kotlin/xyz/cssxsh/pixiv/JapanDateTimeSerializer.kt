package xyz.cssxsh.pixiv

import java.time.format.*
import java.util.*

public object JapanDateTimeSerializer : OffsetDateTimeSerializer {
    override val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.JAPAN)
}
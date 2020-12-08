package xyz.cssxsh.pixiv.data

import java.time.format.DateTimeFormatter
import java.util.*

object JapanDateTimeSerializer : DateTimeSerializer {
    override val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.JAPAN)
}
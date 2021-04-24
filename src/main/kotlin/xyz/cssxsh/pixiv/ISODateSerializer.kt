package xyz.cssxsh.pixiv

import java.time.format.DateTimeFormatter

object ISODateSerializer: LocalDateSerializer {
    override val dateFormat: DateTimeFormatter = DateTimeFormatter.ISO_DATE
}
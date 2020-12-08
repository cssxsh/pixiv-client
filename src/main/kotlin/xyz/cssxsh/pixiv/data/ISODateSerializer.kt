package xyz.cssxsh.pixiv.data

import java.time.format.DateTimeFormatter

object ISODateSerializer: DateSerializer {
    override val dateFormat: DateTimeFormatter = DateTimeFormatter.ISO_DATE
}
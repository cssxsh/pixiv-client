package xyz.cssxsh.pixiv.exception

import io.ktor.utils.io.errors.*

class ByteArrayException(override val message: String) : IOException()
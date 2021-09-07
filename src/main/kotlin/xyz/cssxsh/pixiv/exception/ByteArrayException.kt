package xyz.cssxsh.pixiv.exception

import io.ktor.utils.io.errors.*

class ByteArrayException(val bytes: ByteArray, val expected: Int) : IOException() {
    override val message get() = "Expected ${expected}, actual ${bytes.size}"
}
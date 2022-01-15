package xyz.cssxsh.pixiv.gif

internal fun Int.asUnsignedShort(): Short {
    check(this in 0..0xFFFF)
    return toShort()
}

internal fun Int.asUnsignedByte(): Byte {
    check(this in 0..0xFF)
    return toByte()
}

internal fun Int.asRGBBytes(): ByteArray {
    return byteArrayOf(
        (this and 0xFF0000 shr 16).toByte(),
        (this and 0x00FF00 shr 8).toByte(),
        (this and 0x0000FF).toByte()
    )
}
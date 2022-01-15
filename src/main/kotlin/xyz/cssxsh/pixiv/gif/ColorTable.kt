package xyz.cssxsh.pixiv.gif

import java.nio.ByteBuffer

class ColorTable(
    val colors: IntArray,
    val sort: Boolean = false,
    val background: Int = 0,
) {
    companion object {
        val Sizes = listOf(0, 2, 4, 8, 16, 32, 64, 128, 256)
        val Empty = ColorTable(IntArray(0))
    }

    init {
        // XXX
        check(colors.size in Sizes) { "Size Not" }
        check(colors.isEmpty() || background in colors.indices) { "Background Not" }
    }

    fun write(buffer: ByteBuffer) {
        for (color in colors) {
            buffer.put(color.asRGBBytes())
        }
    }

    fun exists() = colors.isNotEmpty()

    fun size() = when (colors.size) {
        256 -> 0x07
        128 -> 0x06
        64 -> 0x05
        32 -> 0x04
        16 -> 0x03
        8 -> 0x02
        4 -> 0x01
        2 -> 0x00
        0 -> 0x00
        else -> throw IllegalArgumentException("....")
    }
}
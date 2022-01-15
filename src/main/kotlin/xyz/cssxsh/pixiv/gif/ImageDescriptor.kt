package xyz.cssxsh.pixiv.gif

import java.nio.ByteBuffer

object ImageDescriptor {
    private const val SEPARATOR = 0x002C
    private const val TERMINATOR = 0x0000

    private fun block(
        buffer: ByteBuffer,
        left: Short,
        top: Short,
        width: Short,
        height: Short,
        flags: Byte,
    ) {
        buffer.put(SEPARATOR.asUnsignedByte())
        buffer.putShort(left)
        buffer.putShort(top)
        buffer.putShort(width)
        buffer.putShort(height)
        buffer.put(flags)
    }

    private fun data(
        buffer: ByteBuffer,
        min: Int,
        data: ByteArray
    ) {
        buffer.put(min.asUnsignedByte())
        for (index in data.indices step 255) {
            val size = minOf(data.size - index, 255)
            buffer.put(size.asUnsignedByte())
            buffer.put(data, index, size)
        }
    }

    internal fun write(
        buffer: ByteBuffer,
        left: Int,
        top: Int,
        width: Int,
        height: Int,
        colorTable: ColorTable,
        local: Boolean,
        image: IntArray
    ) {
        // Not Interlaced Images
        var flags = 0x00

        if (local) {
            flags = 0x80 or colorTable.size()
            if (colorTable.sort) {
                flags = flags or 0x10
            }
        }

        block(
            buffer = buffer,
            left = left.asUnsignedShort(),
            top = top.asUnsignedShort(),
            width = width.asUnsignedShort(),
            height = height.asUnsignedShort(),
            flags = flags.asUnsignedByte()
        )

        if (local) colorTable.write(buffer)

        val (min, lzw) = LZWEncoder(colorTable, image).encode()

        data(buffer, min, lzw)

        buffer.put(TERMINATOR.asUnsignedByte())
    }
}
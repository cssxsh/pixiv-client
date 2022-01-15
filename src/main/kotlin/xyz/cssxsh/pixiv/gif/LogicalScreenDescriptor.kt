package xyz.cssxsh.pixiv.gif

import java.nio.ByteBuffer

object LogicalScreenDescriptor {

    private fun block(
        buffer: ByteBuffer,
        width: Short,
        height: Short,
        flags: Byte,
        backgroundColorIndex: Byte,
        pixelAspectRatio: Byte
    ) {
        buffer.putShort(width)
        buffer.putShort(height)
        buffer.put(flags)
        buffer.put(backgroundColorIndex)
        buffer.put(pixelAspectRatio)
    }

    internal fun write(
        buffer: ByteBuffer,
        width: Int,
        height: Int,
        colorTable: ColorTable,
        pixelAspectRatio: Int
    ) {
        // Color Resolution Use 7
        var flags = 0x70

        if (colorTable.exists()) {
            flags = flags or 0x80 or colorTable.size()
        }

        if (colorTable.sort) {
            flags = flags or 0x08
        }

        block(
            buffer = buffer,
            width = width.asUnsignedShort(),
            height = height.asUnsignedShort(),
            flags = flags.asUnsignedByte(),
            backgroundColorIndex = colorTable.background.asUnsignedByte(),
            pixelAspectRatio = pixelAspectRatio.asUnsignedByte()
        )

        colorTable.write(buffer)
    }
}
package xyz.cssxsh.pixiv.gif

import java.nio.ByteBuffer

object GraphicControlExtension {
    private const val INTRODUCER = 0x0021
    private const val LABEL = 0x00F9
    private const val BLOCK_SIZE = 0x0004
    private const val TERMINATOR = 0x0000

    private fun block(
        buffer: ByteBuffer,
        flags: Byte,
        delay: Short,
        transparencyIndex: Byte,
    ) {
        buffer.put(INTRODUCER.asUnsignedByte())
        buffer.put(LABEL.asUnsignedByte())
        buffer.put(BLOCK_SIZE.asUnsignedByte())
        buffer.put(flags)
        buffer.putShort(delay)
        buffer.put(transparencyIndex)
        buffer.put(TERMINATOR.asUnsignedByte())
    }

    internal fun write(
        buffer: ByteBuffer,
        disposalMethod: DisposalMethod,
        userInput: Boolean,
        transparencyIndex: Int?,
        delayMills: Int
    ) {
        // Not Interlaced Images
        var flags = 0x0000

        flags = flags or disposalMethod.flag
        if (userInput) {
            flags = flags or 0x0002
        }

        if (transparencyIndex in 0..255) {
            flags = flags or 0x0001
        }

        block(
            buffer = buffer,
            flags = flags.asUnsignedByte(),
            delay = (delayMills / 10).asUnsignedShort(),
            transparencyIndex = (transparencyIndex ?: 0).asUnsignedByte()
        )
    }
}
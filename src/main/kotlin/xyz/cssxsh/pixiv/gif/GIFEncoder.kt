package xyz.cssxsh.pixiv.gif

import java.nio.ByteBuffer

class GIFEncoder {
    companion object {
        const val GIF_HEADER = "GIF89a"
        const val GIF_TRAILER = 0x003B
    }

    internal fun header(buffer: ByteBuffer) {
        for (char in GIF_HEADER) {
            buffer.put(char.code.asUnsignedByte())
        }
    }

    internal fun trailer(buffer: ByteBuffer) {
        buffer.put(GIF_TRAILER.asUnsignedByte())
    }
}
package xyz.cssxsh.pixiv.gif

import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import org.junit.jupiter.api.Test
import xyz.cssxsh.pixiv.PixivJson
import xyz.cssxsh.pixiv.apps.IllustInfo
import xyz.cssxsh.pixiv.apps.UgoiraFrame
import xyz.cssxsh.pixiv.apps.UgoiraMetadata
import java.awt.image.BufferedImage
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.zip.ZipFile
import javax.imageio.ImageIO

class GIFEncoderTest {

    private val mb = 1 shl 20

    private val work = File("../test")
    private val UgoiraMetadata.original get() = Url(zipUrls.values.first().replace("600x600", "1920x1080"))

    private fun UgoiraMetadata.zip(): ZipFile {
        return ZipFile(work.resolve(original.encodedPath.substringAfterLast('/')))
    }

    private infix fun UgoiraFrame.with(zip: ZipFile): BufferedImage = with(zip) {
        getInputStream(getEntry(file)).use { ImageIO.read(it) }
    }

    private fun <T> UgoiraMetadata.frame(block: (UgoiraFrame, BufferedImage) -> T) = zip().use { zip ->
        frames.map { frame ->
            block(frame, frame with zip)
        }
    }

    @Test
    fun encoder() {


        val buffer = ByteBuffer.allocate(mb * 32).order(ByteOrder.LITTLE_ENDIAN)
        val encoder = GIFEncoder()
        val dest = OpenCVQuantizer.quantize(IntArray(1024) { (0..0xFFFFFF).random() }, 256)
        val colorTable = ColorTable(colors = dest)

        encoder.header(buffer)

        LogicalScreenDescriptor.write(
            buffer,
            256,
            256,
            ColorTable.Empty,
            0
        )

        ApplicationExtension.loop(
            buffer,
            0
        )

        for (frame in 0..6) {

            GraphicControlExtension.write(
                buffer,
                DisposalMethod.UNSPECIFIED,
                false,
                null,
                1280
            )

            ImageDescriptor.write(
                buffer,
                0,
                0,
                256,
                256,
                colorTable,
                true,
                IntArray(256 * 256) { colorTable.colors.random() }
            )
        }

        encoder.trailer(buffer)

        File("1234.gif").writeBytes(buffer.let {
            it.array().sliceArray(0..it.position())
        })
    }


    @Test
    fun build() {

        val illust = PixivJson.decodeFromString<IllustInfo>(work.resolve("87036967.json").readText())
        val ugoira = PixivJson.decodeFromString<UgoiraMetadata>(work.resolve("87036967.ugoira.json").readText())

        val buffer = ByteBuffer.allocate(mb * 32).order(ByteOrder.LITTLE_ENDIAN)
        val encoder = GIFEncoder()
        encoder.header(buffer)

        val all = ugoira.frame { _, image ->
            IntArray(image.width * image.height) {
                image.getRGB(it % image.width, it / image.width)
            }
        }.reduce { acc, item -> acc + item }


        val dest = OpenCVQuantizer.quantize(all, 256)

        val table = ColorTable(dest)



        LogicalScreenDescriptor.write(
            buffer,
            illust.width,
            illust.height,
            table,
            0
        )

        ApplicationExtension.loop(
            buffer,
            0
        )

        ugoira.frame { frame, image ->
            GraphicControlExtension.write(
                buffer,
                DisposalMethod.UNSPECIFIED,
                false,
                null,
                frame.delay.toInt()
            )

            val result = AtkinsonDitherer.dither(image, dest)

            ImageDescriptor.write(
                buffer,
                0,
                0,
                image.width,
                image.height,
                table,
                false,
                result
            )
        }

        encoder.trailer(buffer)

        work.resolve("${illust.pid}.gif").writeBytes(buffer.let {
            it.array().sliceArray(0..it.position())
        })

    }
}
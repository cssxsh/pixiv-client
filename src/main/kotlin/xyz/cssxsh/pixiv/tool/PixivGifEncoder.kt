package xyz.cssxsh.pixiv.tool

import com.squareup.gifencoder.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.apps.*
import java.awt.image.*
import java.io.*
import java.util.concurrent.*
import java.util.zip.*
import javax.imageio.*

open class PixivGifEncoder(private val downloader: PixivDownloader = PixivDownloader()) {
    protected open val dir: File = File(".")

    protected open suspend fun UgoiraMetadata.zip(): File {
        val url = Url(zipUrls.values.first().replace("600x600", "1920x1080"))
        return dir.resolve(url.encodedPath.substringAfterLast('/')).apply {
            if (exists().not()) {
                writeBytes(downloader.download(url))
            }
        }
    }

    protected open val quantizer: ColorQuantizer = MedianCutQuantizer.INSTANCE

    protected open val ditherer: Ditherer = FloydSteinbergDitherer.INSTANCE

    protected open val disposalMethod = DisposalMethod.UNSPECIFIED

    private fun BufferedImage.readRGBs() = Array(height) { y -> IntArray(width) { x -> getRGB(x, y) } }

    private fun UgoiraMetadata.Frame.toImageOptions() = ImageOptions().apply {
        setColorQuantizer(quantizer)
        setDitherer(ditherer)
        setDisposalMethod(disposalMethod)
        setDelay(delay, TimeUnit.MILLISECONDS)
    }

    private infix fun UgoiraMetadata.Frame.with(zip: ZipFile) = with(zip) {
        println(file)
        Image.fromRgb(getInputStream(getEntry(file)).use { ImageIO.read(it) }.readRGBs())
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> instance(name: String): T {
        val clazz = Class.forName(name, true, PixivGifEncoder::class.java.classLoader)
        return kotlin.runCatching {
            clazz.getField("INSTANCE").get(clazz)
        }.getOrElse {
            clazz.getConstructor().newInstance()
        } as T
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    open suspend fun encode(pid: Long, metadata: UgoiraMetadata, width: Int, height: Int, loop: Int = 0): File {
        val zip = ZipFile(metadata.zip())
        val gif = dir.resolve("${pid}.gif")
        val output = gif.outputStream().buffered(1 shl 20)

        val encoder = GifEncoder(output, width, height, loop)
        metadata.frames.forEach { frame -> encoder.addImage(frame with zip, frame.toImageOptions()) }

        encoder.finishEncoding()
        zip.close()
        output.close()
        return gif
    }
}
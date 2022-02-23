package xyz.cssxsh.pixiv.tool

import com.squareup.gifencoder.*
import xyz.cssxsh.pixiv.apps.*
import java.awt.image.*
import java.io.*
import java.util.concurrent.*

public open class PixivGifEncoder(override val downloader: PixivDownloader = PixivDownloader()) : PixivUgoiraEncoder() {
    protected open val quantizer: ColorQuantizer = MedianCutQuantizer.INSTANCE

    protected open val ditherer: Ditherer = FloydSteinbergDitherer.INSTANCE

    protected open val disposalMethod: DisposalMethod = DisposalMethod.UNSPECIFIED

    protected open val bufferSize: Int = 1 shl 22

    protected fun BufferedImage.readRGBs(): Array<IntArray> = Array(height) { y -> IntArray(width) { x -> getRGB(x, y) } }

    protected fun UgoiraFrame.toImageOptions(): ImageOptions = ImageOptions().apply {
        setColorQuantizer(quantizer)
        setDitherer(ditherer)
        setDisposalMethod(disposalMethod)
        setDelay(delay, TimeUnit.MILLISECONDS)
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T> instance(name: String): T {
        val clazz = Class.forName(name, true, PixivGifEncoder::class.java.classLoader)
        return try {
            clazz.getField("INSTANCE").get(null)
        } catch (_: Throwable) {
            clazz.getConstructor().newInstance()
        } as T
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun encode(illust: IllustInfo, metadata: UgoiraMetadata, loop: Int): File {
        val temp = cache.resolve("${illust.pid}.temp")
        val output = temp.outputStream().buffered(bufferSize)
        var width = illust.width
        var height = illust.height
        val encoder by lazy { GifEncoder(output, width, height, loop) }

        metadata.frame { frame, image ->
            width = image.width
            height = image.height
            encoder.addImage(image.readRGBs(), frame.toImageOptions())
        }
        (quantizer as? OpenCVQuantizer)?.reset()

        encoder.finishEncoding()
        output.close()

        return cache.resolve("${illust.pid}.gif").apply {
            if (exists()) {
                renameTo(cache.resolve("${illust.pid}.${lastModified()}.gif"))
            }
            temp.renameTo(this)
        }
    }
}
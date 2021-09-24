package xyz.cssxsh.pixiv.tool

import com.squareup.gifencoder.*
import xyz.cssxsh.pixiv.apps.*
import java.awt.image.*
import java.io.*
import java.util.concurrent.*

open class PixivGifEncoder(override val downloader: PixivDownloader = PixivDownloader()) : PixivUgoiraEncoder() {
    protected open val quantizer: ColorQuantizer = MedianCutQuantizer.INSTANCE

    protected open val ditherer: Ditherer = FloydSteinbergDitherer.INSTANCE

    protected open val disposalMethod = DisposalMethod.UNSPECIFIED

    protected fun BufferedImage.readRGBs() = Array(height) { y -> IntArray(width) { x -> getRGB(x, y) } }

    protected fun UgoiraFrame.toImageOptions() = ImageOptions().apply {
        setColorQuantizer(quantizer)
        setDitherer(ditherer)
        setDisposalMethod(disposalMethod)
        setDelay(delay, TimeUnit.MILLISECONDS)
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
    override suspend fun encode(illust: IllustInfo, metadata: UgoiraMetadata, loop: Int): File {
        val gif = dir.resolve("${illust.pid}.gif")
        val output = gif.outputStream().buffered(1 shl 20)
        var width = illust.width
        var height = illust.height
        val encoder by lazy { GifEncoder(output, width, height, loop) }

        metadata.frame { frame, image ->
            width = image.width
            height = image.height
            encoder.addImage(image.readRGBs(), frame.toImageOptions())
        }

        encoder.finishEncoding()
        output.close()
        return gif
    }
}
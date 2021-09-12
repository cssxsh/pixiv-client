package xyz.cssxsh.pixiv.tool

import lunartools.apng.*
import xyz.cssxsh.pixiv.apps.*
import java.io.*

open class PixivPngEncoder(override val downloader: PixivDownloader = PixivDownloader()) : PixivUgoiraEncoder() {
    protected open val quantizer = ApngBuilder.QuantizerAlgorithm.MEDIAN_CUT

    protected open val dithering = ApngBuilder.DitheringAlgorithm.SIERRA

    protected open val encoder = true

    protected open val reencode = true

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun encode(pid: Long, metadata: UgoiraMetadata, width: Int, height: Int, loop: Int): File {
        val png = dir.resolve("${pid}.png")
        val builder = ApngBuilder()
            .setQuantizerAlgorithm(quantizer)
            .setDitheringAlgorithm(dithering)
            .enablePngEncoder(encoder)
            .enableReencodePngFiles(reencode)
        val apng = metadata
            .frame { frame, image -> builder.buildPng(image).apply { delay = frame.delay.toInt() } }
            .reduce { acc, next -> acc.apply { addPng(next) } }

        apng.savePng(png)
        return png
    }
}
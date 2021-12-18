package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import xyz.cssxsh.pixiv.apps.*
import java.awt.image.*
import java.io.*
import java.util.zip.*
import javax.imageio.*

@Suppress("BlockingMethodInNonBlockingContext")
abstract class PixivUgoiraEncoder {
    protected open val cache: File = File(".")

    protected open val downloader: PixivDownloader = PixivDownloader()

    // origin not found in app api
    protected val UgoiraMetadata.original get() = Url(zipUrls.values.first().replace("600x600", "1920x1080"))

    protected open suspend fun download(url: Url, filename: String) = cache.resolve(filename).apply {
        if (exists().not()) {
            writeBytes(downloader.download(url))
        }
    }

    protected open suspend fun UgoiraMetadata.download(): File {
        return download(original, original.encodedPath.substringAfterLast('/'))
    }

    protected infix fun UgoiraFrame.with(zip: ZipFile): BufferedImage = with(zip) {
        getInputStream(getEntry(file)).use { ImageIO.read(it) }
    }

    protected suspend fun <T> UgoiraMetadata.frame(block: (UgoiraFrame, BufferedImage) -> T): List<T> {
        return ZipFile(download()).use { zip ->
            frames.map { frame ->
                block(frame, frame with zip)
            }
        }
    }

    /**
     * write to [PixivUgoiraEncoder.cache] with pid of [illust] as filename
     */
    abstract suspend fun encode(illust: IllustInfo, metadata: UgoiraMetadata, loop: Int = 0): File
}
package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import xyz.cssxsh.pixiv.apps.*
import java.awt.image.*
import java.io.*
import java.util.zip.*
import javax.imageio.*

@Suppress("BlockingMethodInNonBlockingContext")
abstract class PixivUgoiraEncoder {
    protected open val dir: File = File(".")

    protected open val downloader: PixivDownloader = PixivDownloader()

    protected open suspend fun UgoiraMetadata.zip(): ZipFile {
        val url = Url(zipUrls.values.first().replace("600x600", "1920x1080"))
        return dir.resolve(url.encodedPath.substringAfterLast('/')).apply {
            if (exists().not()) {
                writeBytes(downloader.download(url))
            }
        }.let {
            ZipFile(it)
        }
    }

    protected infix fun UgoiraFrame.with(zip: ZipFile): BufferedImage = with(zip) {
        getInputStream(getEntry(file)).use { ImageIO.read(it) }
    }

    protected suspend fun <T> UgoiraMetadata.frame(block: (UgoiraFrame, BufferedImage) -> T) = zip().use { zip ->
        frames.map { frame ->
            block(frame, frame with zip)
        }
    }

    /**
     * write to [PixivUgoiraEncoder.dir] with [pid] as filename
     */
    abstract suspend fun encode(pid: Long, metadata: UgoiraMetadata, width: Int, height: Int, loop: Int = 0): File
}
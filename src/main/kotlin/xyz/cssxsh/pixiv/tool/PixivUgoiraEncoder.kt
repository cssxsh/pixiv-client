package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import kotlinx.coroutines.*
import xyz.cssxsh.pixiv.apps.*
import java.awt.image.*
import java.io.*
import java.util.zip.*
import javax.imageio.*

public abstract class PixivUgoiraEncoder {
    protected open val cache: File = File(".")

    protected open val downloader: PixivDownloader = PixivDownloader()

    // origin not found in app api
    protected val UgoiraMetadata.original: Url get() = Url(zipUrls.values.first().replace("600x600", "1920x1080"))

    protected open suspend fun download(url: Url, filename: String): File = cache.resolve(filename).apply {
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
        val file = download()
        return withContext(Dispatchers.IO) {
            ZipFile(file).use { zip ->
                frames.map { frame ->
                    block(frame, frame with zip)
                }
            }
        }
    }

    /**
     * write to [PixivUgoiraEncoder.cache] with pid of [illust] as filename
     */
    public abstract suspend fun encode(illust: IllustInfo, metadata: UgoiraMetadata, loop: Int = 0): File
}
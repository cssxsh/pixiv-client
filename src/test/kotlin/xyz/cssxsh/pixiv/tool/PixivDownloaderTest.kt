package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import org.junit.jupiter.api.*
import xyz.cssxsh.pixiv.*
import kotlin.system.*

internal class PixivDownloaderTest {

    private val artworks = listOf(
        // https://www.pixiv.net/artworks/103618154
        listOf(
            "https://i.pximg.net/img-original/img/2022/12/15/11/42/31/103618154_p0.png",
            "https://i.pximg.net/img-original/img/2022/12/15/11/42/31/103618154_p1.png",
            "https://i.pximg.net/img-original/img/2022/12/15/11/42/31/103618154_p2.png",
            "https://i.pximg.net/img-original/img/2022/12/15/11/42/31/103618154_p3.png",
            "https://i.pximg.net/img-original/img/2022/12/15/11/42/31/103618154_p4.png",
            "https://i.pximg.net/img-original/img/2022/12/15/11/42/31/103618154_p5.png",
            "https://i.pximg.net/img-original/img/2022/12/15/11/42/31/103618154_p6.png",
        ),
        // https://www.pixiv.net/artworks/103681154
        listOf(
            "https://i.pximg.net/img-original/img/2022/12/17/20/36/32/103681154_p0.png",
            "https://i.pximg.net/img-original/img/2022/12/17/20/36/32/103681154_p1.png",
            "https://i.pximg.net/img-original/img/2022/12/17/20/36/32/103681154_p2.png",
            "https://i.pximg.net/img-original/img/2022/12/17/20/36/32/103681154_p3.png",
            "https://i.pximg.net/img-original/img/2022/12/17/20/36/32/103681154_p4.png"
        )
    )

    private val host = mapOf("i.pximg.net" to (134..147).map { "210.140.92.${it}" })

    private val jobs = (3..8).map { 2 shl it }

    private var temp = Channel<Int>(32)

    private val ByteArray.length
        get() = buildString {
            ((size shr 20) and 0x3FF).let {
                if (it > 0) append("${(size shr 20) and 0x3FF}MB.")
            }
            ((size shr 20) and 0x3FF).let {
                if (it > 0) append("${(size shr 10) and 0x3FF}KB.")
            }
            ((size shr 20) and 0x3FF).let {
                if (it > 0) append("${(size shr 0) and 0x3FF}B.")
            }
        }


    private val downloader = object : PixivDownloader(blockSize = 512 * HTTP_KILO, host = host) {
        override val timeout: Long = 10_000L
        override val channel: Channel<Int> get() = temp
    }

    private suspend fun download(target: List<String>): List<Deferred<Unit>> {
        return downloader.downloadImageUrls(urls = target.map(::Url)) { url, deferred ->
            supervisorScope {
                async {
                    val bytes = deferred.await()
                    println("$url \t| ${bytes.length}")
                }
            }
        }
    }

    @Test
    fun async(): Unit = runBlocking {
        jobs.forEach { async ->
            temp = Channel(capacity = async)
            val millis = measureTimeMillis {
                artworks.flatMap { artwork -> download(target = artwork) }
                    .awaitAll()
            }
            println("async:${async} -> ${java.time.Duration.ofMillis(millis)}")
        }
    }
}
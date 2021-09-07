package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import xyz.cssxsh.pixiv.*
import kotlin.system.*

internal class PixivDownloaderTest {

    // 0 - 52
    private val urls = (0..2).map {
        Url("https://i.pximg.net/img-original/img/2020/09/25/20/03/38/84603624_p$it.jpg")
    }

    private val host = mapOf("i.pximg.net" to (134..147).map { "210.140.92.${it}" })

    private val sizes = (5..10).map { 2 shl it }

    private val timeouts = 30 downTo 10 step 5

    private val nums = (2..8).map { 2 shl it }

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



    private suspend fun download(kilobytes: Int, seconds: Int, async: Int): Int {
        val downloader = object : PixivDownloader(
            async = async,
            blockSize = kilobytes * HTTP_KILO,
            host = host,
        ) {
            override val timeout: Long = seconds * 1000L

            override val ignore: suspend (Throwable) -> Boolean = {
                //println(it)
                super.ignore(it)
            }
        }
        return downloader.downloadImageUrls(urls) { url, deferred ->
            val bytes = deferred.await()
            println("$url \t| ${bytes.length}")
            bytes.size
        }.sum()
    }

    @Test
    fun size(): Unit = runBlocking {
        sizes.forEach { size ->
            measureTimeMillis {
                download(kilobytes = size, seconds = 30, async = 32)
            }.let { time ->
                println("$size : $time")
            }
        }
    }

    @Test
    fun timeout(): Unit = runBlocking {
        timeouts.forEach { timeout ->
            measureTimeMillis {
                download(kilobytes = 512, seconds = timeout, async = 4)
            }.let { time ->
                println("$timeout : $time")
            }
        }
    }

    @Test
    fun async(): Unit = runBlocking {
        nums.forEach { async ->
            measureTimeMillis {
                download(kilobytes = 512, seconds = 10, async = async)
            }.let { time ->
                println("$async : $time")
            }
        }
    }
}
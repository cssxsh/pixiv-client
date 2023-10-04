package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import xyz.cssxsh.pixiv.HTTP_KILO
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

internal class PixivDownloaderTest {

    private val artworks = listOf(
        // https://www.pixiv.net/artworks/104820728
        listOf(
            "https://i.pximg.net/img-original/img/2023/01/26/12/00/37/104820728_p0.png",
            "https://i.pximg.net/img-original/img/2023/01/26/12/00/37/104820728_p1.png",
            "https://i.pximg.net/img-original/img/2023/01/26/12/00/37/104820728_p2.png",
            "https://i.pximg.net/img-original/img/2023/01/26/12/00/37/104820728_p3.png",
            "https://i.pximg.net/img-original/img/2023/01/26/12/00/37/104820728_p4.png",
            "https://i.pximg.net/img-original/img/2023/01/26/12/00/37/104820728_p5.png",
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

    private val jobs = (3..5).map { 2 shl it }

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
        val regex = """(\d+)_p(\d+)""".toRegex()
        return downloader.downloadImageUrls(urls = target.map(::Url)) { url, deferred ->
            supervisorScope {
                async {
                    val bytes: ByteArray
                    // TODO fix measureTimeMillis not found
//                    val millis = measureTimeMillis {
//                        bytes = deferred.await()
//                    }
//                    val (pid, index) = regex.find(url.encodedPath)!!.destructured
//                    val page = """${pid}#${index.toInt().plus(1)}"""
//                    println("| [$page](https://www.pixiv.net/artworks/${page}) | ${bytes.length} | ${millis.milliseconds} |")
                }
            }
        }
    }

//    @Test
    fun async(): Unit = runBlocking {
        println("# Async Test")
        val durations = jobs.associateWith { async ->
            temp = Channel(capacity = async)
            println()
            println("## Async Number $async")
            println("| IMAGE | SIZE | DURATION |")
            println("|:-----:|:----:|:--------:|")
            // TODO fix test
//            val millis = measureTimeMillis {
//                artworks.flatMap { artwork -> download(target = artwork) }
//                    .awaitAll()
//            }
//            millis.milliseconds
        }
        println("## Async Duration")
        println("| ASYNC | DURATION |")
        println("|:-----:|:------:|")
        durations.forEach { (async, duration) ->
            println("| $async | $duration |")
        }
    }
}
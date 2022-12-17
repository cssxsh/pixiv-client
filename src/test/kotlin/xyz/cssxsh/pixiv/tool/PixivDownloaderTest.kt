package xyz.cssxsh.pixiv.tool

import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import org.junit.jupiter.api.*
import xyz.cssxsh.pixiv.*
import java.io.*
import kotlin.system.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PixivDownloaderTest {

    private var stdout: PrintStream? = null

    @BeforeAll
    fun init() {
        val markdown = File(System.getenv("GITHUB_STEP_SUMMARY") ?: "run/summary.md")
        if (markdown.exists()) {
            stdout = System.out
            System.setOut(PrintStream(markdown))
        }
    }

    @AfterAll
    fun redirect() {
        if (stdout != null) {
            System.setOut(stdout)
        }
    }

    init {
        val markdown = File(System.getenv("GITHUB_STEP_SUMMARY") ?: "run/summary.md")
        if (markdown.exists()) {
            System.setOut(PrintStream(markdown))
        }
    }

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
        val regex = """(\d+)_p(\d+)""".toRegex()
        return downloader.downloadImageUrls(urls = target.map(::Url)) { url, deferred ->
            supervisorScope {
                async {
                    val bytes: ByteArray
                    val millis = measureTimeMillis {
                        bytes = deferred.await()
                    }
                    val (pid, index) = regex.find(url.encodedPath)!!.destructured
                    val page = """${pid}#${index.toInt().plus(1)}"""
                    println("| [$page](https://www.pixiv.net/artworks/${page}) | ${bytes.length} | ${java.time.Duration.ofMillis(millis)} |")
                }
            }
        }
    }

    @Test
    fun async(): Unit = runBlocking {
        println("# Async Test")
        val durations = jobs.associateWith { async ->
            temp = Channel(capacity = async)
            println()
            println("## Async Number $async")
            println("| IMAGE | SIZE | DURATION |")
            println("|:-----:|:----:|:--------:|")
            val millis = measureTimeMillis {
                artworks.flatMap { artwork -> download(target = artwork) }
                    .awaitAll()
            }
            java.time.Duration.ofMillis(millis)
        }
        println("## Async Duration")
        println("| ASYNC | DURATION |")
        println("|:-----:|:------:|")
        durations.forEach { (async, duration) ->
            println("| $async | $duration |")
        }
    }
}
package xyz.cssxsh.pixiv.tool

import io.ktor.client.features.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.runBlocking
import okhttp3.internal.http2.StreamResetException
import org.junit.jupiter.api.Test
import java.io.EOFException
import java.io.File
import java.net.ConnectException
import java.time.Duration
import javax.net.ssl.SSLException
import kotlin.system.measureTimeMillis

internal class PixivDownloaderTest {

    private val dir = File("../test/")

    private val urls = listOf(
        "https://i.pximg.net/img-original/img/2020/12/17/02/25/49/86346067_p0.jpg",
        "https://i.pximg.net/img-original/img/2020/12/17/02/25/49/86346067_p1.jpg",
        "https://i.pximg.net/img-original/img/2020/12/17/02/25/49/86346067_p2.jpg",
        "https://i.pximg.net/img-original/img/2020/12/17/02/25/49/86346067_p3.jpg",
        "https://i.pximg.net/img-original/img/2020/12/17/02/25/49/86346067_p4.jpg"
    )

    private val ignore: (String, Throwable, String) -> Boolean = { url, throwable, info ->
        // println("[${url}]<${info}>: ${throwable.message}")
        when (throwable) {
            is SSLException,
            is EOFException,
            is ConnectException,
            is SocketTimeoutException,
            is HttpRequestTimeoutException,
            is StreamResetException,
            is ClosedReceiveChannelException,
            -> {
                true
            }
            else -> when (throwable.message) {
                "Required SETTINGS preface not received" -> {
                    true
                }
                else -> false
            }
        }
    }

    private val host = mapOf(
        "i.pximg.net" to (134..147).map {
            "210.140.92.${it}"
        }
    )

    private val sizes = listOf(
        128,
        256,
        512,
        1024
    )

    private suspend fun downloadImageUrls_(blockSizeKB: Int) {
        PixivDownloader(
            maxUrlAsyncNum = 16,
            host = host,
            ignore = ignore,
            blockSize = blockSizeKB * 1024
        ).downloadImageUrls(urls) { index, url, result ->
            result.getOrThrow().let {
                println("$blockSizeKB \t| $index \t| $url \t| ${it.size}")
                dir.resolve("${blockSizeKB}-${index}.jpg").writeBytes(it)
            }
        }
    }

    @Test
    fun downloadImageUrls(): Unit = runBlocking {
        sizes.forEach { size ->
            measureTimeMillis {
                downloadImageUrls_(blockSizeKB = size)
            }.let { millis ->
                println("${size}KB : ${Duration.ofMillis(millis)}")
            }
        }
    }
}
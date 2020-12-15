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
import javax.net.ssl.SSLException
import kotlin.system.measureTimeMillis

internal class PixivDownloaderTest {

    private val dir = File("../test/")

    private val urls = listOf(
        "https://i.pximg.net/img-original/img/2020/12/13/10/50/44/86269687_p1.jpg",
        "https://i.pximg.net/img-original/img/2019/08/11/13/01/07/76196814_p4.jpg",
        "https://i.pximg.net/img-original/img/2019/07/25/13/48/38/75900340_p1.jpg"
    )

    private val ignore: (String, Throwable, String) -> Boolean = { _, throwable, _ ->
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

    private suspend fun downloadImageUrls_(blockSizeKB: Int) {
        PixivDownloader(
            maxAsyncNum = 128,
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
        measureTimeMillis {
            downloadImageUrls_(1024)
        }.let {
            println("1024KB : ${it}ms")
        }

        measureTimeMillis {
            downloadImageUrls_(512)
        }.let {
            println("512KB : ${it}ms")
        }

        measureTimeMillis {
            downloadImageUrls_(254)
        }.let {
            println("256KB : ${it}ms")
        }

        measureTimeMillis {
            downloadImageUrls_(128)
        }.let {
            println("128KB : ${it}ms")
        }
    }
}
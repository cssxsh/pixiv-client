package xyz.cssxsh.pixiv.tool

import io.ktor.client.features.*
import io.ktor.http.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.runBlocking
import okhttp3.internal.http2.ConnectionShutdownException
import okhttp3.internal.http2.StreamResetException
import org.junit.jupiter.api.Test
import java.io.EOFException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import kotlin.time.*

internal class PixivDownloaderTest {

    // 0 - 52
    private val urls = (0..7).map {
        Url("https://i.pximg.net/img-original/img/2020/09/25/20/03/38/84603624_p$it.jpg")
    }

    private val ignore: suspend (Throwable) -> Boolean = { throwable ->
        // println(throwable.message)
        when (throwable) {
            is SSLException,
            is EOFException,
            is ConnectException,
            is SocketTimeoutException,
            is HttpRequestTimeoutException,
            is StreamResetException,
            is ClosedReceiveChannelException,
            is NullPointerException,
            is UnknownHostException,
            is ConnectionShutdownException,
            -> true
            else -> when {
                throwable.message?.contains("Required SETTINGS preface not received") == true -> true
                throwable.message?.contains("Completed read overflow") == true -> true
                throwable.message?.contains("""Expected \d+, actual \d+""".toRegex()) == true -> true
                else -> false
            }
        }
    }

    private val host = mapOf("i.pximg.net" to (134..147).map { "210.140.92.${it}" })

    private val sizes = listOf(
        32,
        64,
        128,
        256,
        512,
        1024
    )

    private val timeouts = listOf(
        (30).seconds,
        (25).seconds,
        (20).seconds,
        (17).seconds,
        (15).seconds,
        (12).seconds,
        (10).seconds,
        (8).seconds,
        (5).seconds,
    )

    private val nums = listOf(
        4,
        8,
        16,
        32,
        64,
        128,
        256
    )

    private val ByteArray.length get() = buildString {
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

    private suspend fun download(kilobytes: Int, timeout: Duration, async: Int): Int = PixivDownloader(
        async = async,
        initHost = host,
        ignore = ignore,
        kilobytes = kilobytes,
        timeout = timeout
    ).downloadImageUrls(urls) { url, result ->
        println("$url \t| ${result.getOrThrow().length}")
        result.getOrThrow().size
    }.sum()

    @Test
    fun size(): Unit = runBlocking {
        sizes.forEach { size ->
            measureTime {
                download(kilobytes = size, timeout = (1).seconds, async = 32)
            }.let { time ->
                println("$size : $time")
            }
        }
    }

    @Test
    fun timeout(): Unit = runBlocking {
        timeouts.forEach { timeout ->
            measureTime {
                download(kilobytes = 512, timeout = timeout, async = 32)
            }.let { time ->
                println("$timeout : $time")
            }
        }
    }

    @Test
    fun async(): Unit = runBlocking {
        nums.forEach { async ->
            measureTime {
                download(kilobytes = 512, timeout = (10).seconds, async = async)
            }.let { time ->
                println("$async : $time")
            }
        }
    }
}
package xyz.cssxsh.pixiv.tool

import io.ktor.client.features.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.internal.http2.ConnectionShutdownException
import okhttp3.internal.http2.StreamResetException
import org.junit.jupiter.api.Test
import java.io.EOFException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.logging.Level
import java.util.logging.Logger
import javax.net.ssl.SSLException
import kotlin.time.measureTime

internal class PixivDownloaderTest {

    init {
        Logger.getLogger(OkHttpClient::class.java.name).level = Level.OFF
    }

    // 0 - 52
    private val urls = (0..4).map {
        "https://i.pximg.net/img-original/img/2020/09/25/20/03/38/84603624_p$it.jpg"
    }

    private val ignore: (String, Throwable, String) -> Boolean = { _, throwable, _ ->
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

    private val host = mapOf(
        "i.pximg.net" to (134..147).map {
            "210.140.92.${it}"
        }
    )

    private val sizes = listOf(
        256,
        512,
        1024
    )

    private suspend fun downloadImageUrls_(blockSizeKB: Int): Int  = PixivDownloader(
        maxUrlAsyncNum = 16,
        initHost = host,
        ignore = ignore,
        blockSize = blockSizeKB * 1024,
    ).downloadImageUrls(urls) { url, result ->
        println("$blockSizeKB \t| $url \t| ${result.getOrThrow().size}")
        result.getOrThrow().size
    }.sum()

    @Test
    fun downloadImageUrls(): Unit = runBlocking {
        sizes.forEach { size ->
            measureTime {
                downloadImageUrls_(blockSizeKB = size)
            }.let { time ->
                println("${size}KB : $time")
            }
        }
    }

    @Test
    fun flowTest(): Unit = runBlocking {
        measureTime {
            (1..10).asFlow().buffer(4).onEach {
                delay(it % 2 * 1_000L)
                println(it)
            }.collect {
                delay(it % 2 * 1_000L)
                println(it)
            }
        }.let { time ->
            println(time)
        }
    }

    @Test
    fun channelFlowTest(): Unit = runBlocking {
        val subject = Channel<Int>(16)
        val channelFlow = subject.receiveAsFlow()
        launch {
            measureTime {
                channelFlow.collect {
                    delay(1_000)
                    println("subject one: $it")
                }
            }.let {
                println(it)
            }
        }
        repeat(16) {
            subject.send(it)
        }
        //注意只有Channel关闭了runBlocking协程才结束
        subject.close()
    }
}
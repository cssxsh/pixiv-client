package xyz.cssxsh.pixiv

import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.io.*

internal class OtherAuthKtTest {

    private val client = SimplePixivClient(config = PixivConfig())

    private val work = File("../test")

    @Test
    @Suppress("BlockingMethodInNonBlockingContext")
    fun sina(): Unit = runBlocking {
        client.sina { qrcode ->
            work.resolve("qrcode.jpg").writeBytes(client.useHttpClient { it.get(qrcode) })
        }
    }

    @Test
    fun cookie() {
    }
}
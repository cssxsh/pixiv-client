package xyz.cssxsh.pixiv.client

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*

internal class PixivClientTest {
    val pixivClient = SimplePixivClient("http://127.0.0.1:7890")

    @Test
    fun login() = runBlocking<Unit> {
        pixivClient.login("cssxsh@gmail.com", "13087978089")
        assert(pixivClient.isLogined())
        val refreshToken = pixivClient.getRefreshToken()
        pixivClient.refresh(refreshToken)
        assert(pixivClient.isLogined())
    }
}
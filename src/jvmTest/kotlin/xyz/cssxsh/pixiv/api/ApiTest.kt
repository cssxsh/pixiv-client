package xyz.cssxsh.pixiv.api

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.BeforeAll
import xyz.cssxsh.pixiv.client.SimplePixivClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ApiTest {
    val pixivClient = SimplePixivClient {
        refreshToken = "dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY"
    }

    @BeforeAll
    fun setUp() = runBlocking {
        pixivClient.refresh()
        println(pixivClient.authInfo)
    }
}
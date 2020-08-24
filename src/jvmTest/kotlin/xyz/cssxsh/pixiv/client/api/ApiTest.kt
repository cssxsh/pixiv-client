package xyz.cssxsh.pixiv.client.api

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import xyz.cssxsh.pixiv.client.SimplePixivClient
import xyz.cssxsh.pixiv.client.api.aapi.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ApiTest {
    val pixivClient = SimplePixivClient("http://127.0.0.1:7890")

    @BeforeAll
    fun setUp() = runBlocking<Unit> {
        pixivClient.refresh("dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY")
        pixivClient.checkLogin()
    }
}
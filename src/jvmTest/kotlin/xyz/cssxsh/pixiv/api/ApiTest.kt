package xyz.cssxsh.pixiv.api

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.BeforeAll
import xyz.cssxsh.pixiv.client.SimplePixivClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ApiTest {
    val pixivClient = SimplePixivClient()

    @BeforeAll
    fun setUp() = runBlocking {
        pixivClient.proxy = "http://127.0.0.1:7890"
        pixivClient.refresh("dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY")
        pixivClient.checkLogin()
        println(pixivClient.authInfo)
    }
}
package xyz.cssxsh.pixiv.client.api

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.BeforeAll
import xyz.cssxsh.pixiv.client.SimplePixivClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ApiTest {
    val pixivClient = SimplePixivClient("http://127.0.0.1:7890")

    @BeforeAll
    fun setUp() = runBlocking {
        pixivClient.refresh("dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY")
        pixivClient.checkLogin()
        println(pixivClient.authInfo?.accessToken)
    }
}
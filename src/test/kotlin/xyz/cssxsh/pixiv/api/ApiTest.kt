package xyz.cssxsh.pixiv.api

import org.junit.jupiter.api.TestInstance
import xyz.cssxsh.pixiv.client.SimplePixivClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ApiTest {
    val client = SimplePixivClient {
        RubySSLFactory = true
        refreshToken = "dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY"
    }
}
package xyz.cssxsh.pixiv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.BeforeAll
import xyz.cssxsh.pixiv.client.SimplePixivClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ApiTest {
    val pixivClient = SimplePixivClient {
        // refreshToken = "dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY"
        proxy = "http://10.21.159.95:7890"
        RubySSLFactory = false
        refreshToken = "8BdkCQAa_VbtR4VC7Ou7ov3Hgs4ag6gqxQ8Ye74Czfg"
    }

    @BeforeAll
    fun setUp() = runBlocking {
        pixivClient.refresh("8BdkCQAa_VbtR4VC7Ou7ov3Hgs4ag6gqxQ8Ye74Czfg")
        println(pixivClient.authInfo)
    }
}
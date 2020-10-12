package xyz.cssxsh.pixiv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.BeforeAll
import xyz.cssxsh.pixiv.client.SimplePixivClient

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ApiTest {
    val pixivClient = SimplePixivClient {
        // refreshToken = "dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY"
        proxy = "socks5://10.21.159.95:7891"
        RubySSLFactory = true
        refreshToken = "8BdkCQAa_VbtR4VC7Ou7ov3Hgs4ag6gqxQ8Ye74Czfg"
    }

    @BeforeAll
    fun setUp() = runBlocking {

        System.setProperty("javax.net.debug", "ssl")
        System.setProperty("https.protocols", "TLSv1")

        pixivClient.refresh("8BdkCQAa_VbtR4VC7Ou7ov3Hgs4ag6gqxQ8Ye74Czfg")
    }
}
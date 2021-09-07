package xyz.cssxsh.pixiv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ApiTest {

    protected val client = SimplePixivClient(
        config = PixivConfig(refreshToken = System.getenv("PIXIV_TOKEN"))
    )

    @BeforeAll
    fun showAuthInfo(): Unit = runBlocking {
        println(client.config)
    }
}
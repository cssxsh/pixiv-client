package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import xyz.cssxsh.pixiv.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class AppApiKtTest {

    protected val client: PixivAuthClient = object : SimplePixivClient(
        config = PixivConfig(refreshToken = System.getenv("PIXIV_TOKEN"))
    ) {
        override val ignore: suspend (Throwable) -> Boolean = { cause ->
            cause.printStackTrace()
            super.ignore(cause)
        }
    }
}
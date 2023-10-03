package xyz.cssxsh.pixiv.apps

import org.junit.jupiter.api.TestInstance
import xyz.cssxsh.pixiv.PixivAuthClient
import xyz.cssxsh.pixiv.PixivConfig
import xyz.cssxsh.pixiv.SimplePixivClient
import xyz.cssxsh.pixiv.pixiv_client.BuildConfig

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class AppApiKtTest {

    init {
        System.setProperty("xyz.cssxsh.pixiv.json.ignore", "false")
    }

    protected val client: PixivAuthClient = object : SimplePixivClient(
        config = PixivConfig(refreshToken = BuildConfig.REFRESH_TOKEN)
    ) {
        override val ignore: suspend (Throwable) -> Boolean = { cause ->
            cause.printStackTrace()
            super.ignore(cause)
        }
    }
}
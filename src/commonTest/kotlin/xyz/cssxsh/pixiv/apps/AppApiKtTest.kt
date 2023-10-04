package xyz.cssxsh.pixiv.apps

import xyz.cssxsh.pixiv.PixivAuthClient
import xyz.cssxsh.pixiv.PixivConfig
import xyz.cssxsh.pixiv.SimplePixivClient
import xyz.cssxsh.pixiv.pixiv_client.BuildConfig

internal abstract class AppApiKtTest {

    protected val client: PixivAuthClient = object : SimplePixivClient(
        config = PixivConfig(refreshToken = BuildConfig.REFRESH_TOKEN)
    ) {
        override val ignore: suspend (Throwable) -> Boolean = { cause ->
            cause.printStackTrace()
            super.ignore(cause)
        }
    }
}
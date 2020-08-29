package xyz.cssxsh.pixiv.client

import xyz.cssxsh.pixiv.HeadersMap

object Util {
    const val CLIENT_ID: String = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
    const val CLIENT_SECRET: String = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"
    const val HASH_SECRET: String = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c"
    const val OAUTH_URL: String = "https://oauth.secure.pixiv.net/auth/token"
    val IOS_HEADERS: HeadersMap = mapOf(
        "User-Agent" to "PixivIOSApp/6.0.4 (iOS 9.0.2; iPhone6,1)",
        "App-OS-Version" to "9.0.2",
        "App-OS" to "ios",
        "App-Version" to "6.0.4"
    )
    val ANDROID_HEADERS: HeadersMap = mapOf(
        "User-Agent" to "PixivAndroidApp/5.0.64 (Android 6.0)",
        "App-OS-Version" to "6.0",
        "App-OS" to "android",
        "App-Version" to "5.0.64"
    )

    fun PixivConfig(block: PixivConfig.() -> Unit): PixivConfig = PixivConfig().apply(block)
}
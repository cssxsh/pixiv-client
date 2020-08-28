package xyz.cssxsh.pixiv.client

import xyz.cssxsh.pixiv.HeadersMap

object Util {
    const val CLIENT_ID: String = "MOBrBDS8blbauoSck0ZfDbtuzpyT"
    const val CLIENT_SECRET: String = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj"
    const val HASH_SECRET: String = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c"
    const val OAUTH_URL: String = "https://oauth.secure.pixiv.net/auth/token"
    val DEFAULT_HEADERS_MAP: HeadersMap = mapOf(
        "User-Agent" to "PixivAndroidApp/5.0.64 (Android 6.0)",
        "Accept-Language" to "jp"
    )
}
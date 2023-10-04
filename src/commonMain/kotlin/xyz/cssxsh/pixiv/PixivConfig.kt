package xyz.cssxsh.pixiv

public data class PixivConfig(
    var headers: HeadersMap = ANDROID_HEADERS,
    var proxy: String = "",
    var dns: String = JAPAN_DNS,
    var host: Map<String, List<String>> = DEFAULT_PIXIV_HOST,
    var sni: Boolean = true,
    var refreshToken: String? = null,
)
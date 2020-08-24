package xyz.cssxsh.pixiv.client

/**
 * Simple pixiv client implementation
 * @param proxyUrl For example http://127.0.0.1:1080
 * @param acceptLanguage HTTP Header Accept-Language
 * @see <a href="https://developer.mozilla.org/docs/Web/HTTP/Headers/Accept-Language">Accept-Language<a>
 */
actual class SimplePixivClient actual constructor(proxyUrl: String?, acceptLanguage: String) : PixivClient
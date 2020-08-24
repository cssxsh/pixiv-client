package xyz.cssxsh.pixiv.client

/**
 * Simple pixiv client implementation
 * @param proxyUrl For example http://127.0.0.1:1080
 */
actual class SimplePixivClient actual constructor(proxyUrl: String?, acceptLanguage: String) : PixivClient
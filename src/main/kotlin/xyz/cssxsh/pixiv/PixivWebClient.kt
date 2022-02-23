package xyz.cssxsh.pixiv

import io.ktor.client.features.cookies.*

public interface PixivWebClient : UseHttpClient, UseConfig {
    public val storage: CookiesStorage
}
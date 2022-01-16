package xyz.cssxsh.pixiv

import io.ktor.client.features.cookies.*

interface PixivWebClient : UseHttpClient, UseConfig {
    val storage: CookiesStorage
}
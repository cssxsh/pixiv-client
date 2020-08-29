package xyz.cssxsh.pixiv.api.app

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.UgoiraMetadata

suspend fun PixivClient.ugoiraMetadata(
    pid: Long
): UgoiraMetadata = httpClient.get(AppApiUrls.ugoiraMetadata) {
    parameter("illust_id", pid)
}

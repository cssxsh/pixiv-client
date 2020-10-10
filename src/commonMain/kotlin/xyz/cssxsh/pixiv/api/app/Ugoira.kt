package xyz.cssxsh.pixiv.api.app

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.UgoiraMetadata
import xyz.cssxsh.pixiv.useHttpClient

suspend fun PixivClient.ugoiraMetadata(
    pid: Long
): UgoiraMetadata = useHttpClient { client ->
    client.get(AppApiUrls.ugoiraMetadata) {
        parameter("illust_id", pid)
    }
}

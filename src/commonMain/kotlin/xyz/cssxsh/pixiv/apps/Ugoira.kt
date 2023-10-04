package xyz.cssxsh.pixiv.apps

import io.ktor.client.call.*
import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*

public suspend fun PixivAppClient.ugoiraMetadata(
    pid: Long,
    url: String = UGOIRA_METADATA,
): UgoiraInfo = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid)
    }.body()
}

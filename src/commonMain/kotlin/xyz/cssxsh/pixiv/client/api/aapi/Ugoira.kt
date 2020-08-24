package xyz.cssxsh.pixiv.client.api.aapi

import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.client.data.aapi.UgoiraMetadata

suspend fun PixivClient.getUgoiraMetadata(
    pid: Long
): UgoiraMetadata = httpGet(
    deserialize = UgoiraMetadata.serializer(),
    apiUrl = AppApiUrls.ugoiraMetadata,
    paramsMap = mapOf(
        "illust_id" to pid
    )
)

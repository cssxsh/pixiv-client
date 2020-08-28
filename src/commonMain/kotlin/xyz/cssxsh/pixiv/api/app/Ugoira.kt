package xyz.cssxsh.pixiv.api.app

import xyz.cssxsh.pixiv.Method
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.UgoiraMetadata

suspend fun PixivClient.ugoiraMetadata(
    pid: Long
): UgoiraMetadata = useRESTful(
    method = Method.GET,
    deserializer = UgoiraMetadata.serializer(),
    apiUrl = AppApiUrls.ugoiraMetadata,
    paramsMap = mapOf(
        "illust_id" to pid
    )
)

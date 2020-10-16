package xyz.cssxsh.pixiv.api.app

import io.ktor.client.request.*
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.BookmarkDetailSingle
import xyz.cssxsh.pixiv.useHttpClient

suspend fun PixivClient.novelBookmarkDetail(
    pid: Long,
    url: String = AppApi.NOVEL_BOOKMARK_DETAIL
): BookmarkDetailSingle = useHttpClient { client ->
    client.get(url) {
        parameter("illust_id", pid)
    }
}
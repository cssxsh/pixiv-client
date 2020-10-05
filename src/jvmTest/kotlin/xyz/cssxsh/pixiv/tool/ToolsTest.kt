package xyz.cssxsh.pixiv.tool

import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import xyz.cssxsh.pixiv.ApiTest
import xyz.cssxsh.pixiv.api.app.illustDetail

internal class ToolsTest : ApiTest() {
    @Test
    fun downloadImage(): Unit = runBlocking {
        val data = pixivClient.illustDetail(83919385)
        pixivClient.downloadImage<HttpResponse>(illust = data.illust) { httpResponse ->
            val length = httpResponse.contentLength()
            assert(httpResponse.status.value == 200) {
                "状态码不正确"
            }
            val bytes = runBlocking {
                httpResponse.readBytes()
            }
            assert(bytes.size.toLong() == length) {
                "文件大小不一致"
            }
        }
    }

}
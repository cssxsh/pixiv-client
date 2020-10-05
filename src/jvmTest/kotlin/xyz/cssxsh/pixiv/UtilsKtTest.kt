package xyz.cssxsh.pixiv

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import xyz.cssxsh.pixiv.api.app.illustDetail
import xyz.cssxsh.pixiv.client.SimplePixivClient
import xyz.cssxsh.pixiv.data.app.IllustSingle
import xyz.cssxsh.pixiv.tool.downloadImage

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UtilsKtTest {
    private val pixivClient = SimplePixivClient {
        proxy = "http://127.0.0.1:7890"
        refreshToken = "dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY"
    }

    @BeforeAll
    fun setUp() = runBlocking {
        pixivClient.refresh("dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY")
        println(pixivClient.authInfo)
    }

    @Test
    fun download() = runBlocking {
        val data: IllustSingle = pixivClient.illustDetail(83919385L)
        val result: List<Result<ByteArray>> = pixivClient.downloadImage(data.illust, { name, _ -> "origin" in name })
        result.forEach {
            it.onSuccess { bytes ->
                print(bytes.size)
            }.onFailure { error ->
                print(error.message)
            }
        }
    }
}
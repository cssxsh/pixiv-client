package xyz.cssxsh.pixiv.client.api.aapi

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.client.ContentType
import xyz.cssxsh.pixiv.client.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class IllustTest: ApiTest() {
    @Test
    fun getIllustFollow() = runBlocking {
        val data = pixivClient.getIllustFollow()
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun getIllustNew() = runBlocking {
        val data = pixivClient.getIllustNew(contentType = ContentType.MANGA)
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun getIllustMypixiv() = runBlocking {
        val data = pixivClient.getIllustMypixiv()
        Assertions.assertTrue(data.illusts.isEmpty())
    }
}
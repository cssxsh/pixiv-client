package xyz.cssxsh.pixiv.api.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UgoiraTest : ApiTest() {
    @Test
    fun getUgoiraMetadata(): Unit = runBlocking {
        val data = client.ugoiraMetadata(79007274L)
        Assertions.assertTrue(data.ugoiraMetadata.frames.isNotEmpty())
        Assertions.assertTrue(data.ugoiraMetadata.zipUrls.isNotEmpty())
    }
}
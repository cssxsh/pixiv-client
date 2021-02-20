package xyz.cssxsh.pixiv.api.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.api.ApiTest

internal class UgoiraKtTest : ApiTest() {

    @Test
    fun ugoiraMetadata(): Unit = runBlocking {
        val data = client.ugoiraMetadata(79007274L)
        assertTrue(data.ugoiraMetadata.frames.isNotEmpty())
        assertTrue(data.ugoiraMetadata.zipUrls.isNotEmpty())
    }
}
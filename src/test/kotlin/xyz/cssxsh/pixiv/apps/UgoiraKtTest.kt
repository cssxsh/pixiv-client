package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.ApiTest

internal class UgoiraKtTest : ApiTest() {

    @Test
    fun ugoiraMetadata(): Unit = runBlocking {
        client.ugoiraMetadata(79007274L).ugoira.let {
            assertTrue(it.frames.isNotEmpty())
            assertTrue(it.zipUrls.isNotEmpty())
        }
    }
}
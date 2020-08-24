package xyz.cssxsh.pixiv.client.api.aapi

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import xyz.cssxsh.pixiv.client.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UgoiraTest: ApiTest() {
    @Test
    fun getUgoiraMetadata() = runBlocking<Unit> {
        pixivClient.getUgoiraMetadata(79007274L)
    }
}
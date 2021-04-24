package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.ApiTest

internal class SpotlightKtTest: ApiTest() {

    @Test
    fun spotlightArticles(): Unit = runBlocking {
        client.spotlightArticles().articles.let {
            assertTrue(it.isNotEmpty())
        }
    }
}
package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.ApiTest

internal class TrendingKtTest : ApiTest() {

    @Test
    fun trendingTagsIllust(): Unit = runBlocking {
        client.trendingTagsIllust().trends.let {
            assertTrue(it.isNotEmpty())
        }
    }
}
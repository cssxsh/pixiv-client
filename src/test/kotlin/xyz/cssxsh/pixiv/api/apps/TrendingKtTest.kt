package xyz.cssxsh.pixiv.api.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.api.ApiTest

internal class TrendingKtTest : ApiTest() {

    @Test
    fun trendingTagsIllust(): Unit = runBlocking {
        val data = client.trendingTagsIllust()
        assertTrue(data.trendTags.isNotEmpty())
    }

    @Test
    fun trendingTagsNovel(): Unit = runBlocking {
        val data = client.trendingTagsNovel()
        assertTrue(data.trendTags.isNotEmpty())
    }
}
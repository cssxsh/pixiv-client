package xyz.cssxsh.pixiv.api.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TrendingTest : ApiTest() {
    @Test
    fun getTrendingTagsIllust(): Unit = runBlocking {
        val data = client.trendingTagsIllust()
        Assertions.assertTrue(data.trendTags.isNotEmpty())
    }

    @Test
    fun getTrendingTagsNovel(): Unit = runBlocking {
        val data = client.trendingTagsNovel()
        Assertions.assertTrue(data.trendTags.isNotEmpty())
    }
}
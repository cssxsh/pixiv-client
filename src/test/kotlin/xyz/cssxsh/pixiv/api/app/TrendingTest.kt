package xyz.cssxsh.pixiv.api.app

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TrendingTest: ApiTest() {
    @Test
    fun getTrendingTagsIllust(): Unit = runBlocking {
        val data = pixivClient.trendingTagsIllust()
        Assertions.assertTrue(data.trendTags.isNotEmpty())
    }

    @Test
    fun getTrendingTagsNovel(): Unit = runBlocking {
        val data = pixivClient.trendingTagsNovel()
        Assertions.assertTrue(data.trendTags.isNotEmpty())
    }
}
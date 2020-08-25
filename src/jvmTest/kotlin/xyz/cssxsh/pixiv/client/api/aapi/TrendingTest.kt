package xyz.cssxsh.pixiv.client.api.aapi

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.client.WorkType
import xyz.cssxsh.pixiv.client.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TrendingTest: ApiTest() {
    @Test
    fun getTrendingTagsIllust() = runBlocking {
        val data = pixivClient.getTrendingTags(WorkType.ILLUST)
        Assertions.assertTrue(data.trendTags.isNotEmpty())
    }

    @Test
    fun getTrendingTagsNovel() = runBlocking<Unit> {
        val data = pixivClient.getTrendingTags(WorkType.NOVEL)
        Assertions.assertTrue(data.trendTags.isNotEmpty())
    }
}
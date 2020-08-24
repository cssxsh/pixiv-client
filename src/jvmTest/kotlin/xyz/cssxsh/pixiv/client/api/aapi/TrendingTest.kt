package xyz.cssxsh.pixiv.client.api.aapi

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import xyz.cssxsh.pixiv.client.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TrendingTest: ApiTest() {
    @Test
    fun getTrendingTagsIllust() = runBlocking<Unit> {
        pixivClient.getTrendingTagsIllust()
    }

    @Test
    fun getTrendingTagsNovel() = runBlocking<Unit> {
        pixivClient.getTrendingTagsNovel()
    }
}
package xyz.cssxsh.pixiv.api.app

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import xyz.cssxsh.pixiv.api.ApiTest

class NovelTest: ApiTest() {

    @Test
    fun getNovelBookmarkDetail(): Unit = runBlocking {
        val data = pixivClient.novelBookmarkDetail(83919385)
        Assertions.assertFalse(data.bookmarkDetail.isBookmarked)
    }
}
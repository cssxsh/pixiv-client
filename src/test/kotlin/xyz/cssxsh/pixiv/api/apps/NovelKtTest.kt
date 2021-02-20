package xyz.cssxsh.pixiv.api.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import xyz.cssxsh.pixiv.api.ApiTest

class NovelKtTest : ApiTest() {

    @Test
    fun novelBookmarkDetail(): Unit = runBlocking {
        val data = client.novelBookmarkDetail(83919385)
        assertFalse(data.bookmarkDetail.isBookmarked)
    }
}
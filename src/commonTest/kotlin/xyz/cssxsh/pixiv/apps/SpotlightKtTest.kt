package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

internal class SpotlightKtTest: AppApiKtTest() {

    @Test
    fun `spotlight articles`(): Unit = runBlocking {
        client.spotlightArticles().let { (articles) ->
            assertFalse(articles.isEmpty())
            assertEquals(ARTICLE_PAGE_SIZE, articles.size.toLong())
        }
    }
}
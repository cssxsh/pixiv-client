package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SpotlightKtTest: AppApiKtTest() {

    @Test
    fun `spotlight articles`(): Unit = runBlocking {
        client.spotlightArticles().let { (articles) ->
            assertFalse(articles.isEmpty())
            assertEquals(ARTICLE_PAGE_SIZE, articles.size.toLong())
        }
    }
}
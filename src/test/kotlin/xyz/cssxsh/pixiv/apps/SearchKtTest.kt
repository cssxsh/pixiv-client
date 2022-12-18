package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SearchKtTest : AppApiKtTest() {

    @Test
    fun illust(): Unit = runBlocking {
        client.searchIllust("nmsl").let { (illusts) ->
            assertFalse(illusts.isEmpty())
        }
    }

    @Test
    fun `auto complete`(): Unit = runBlocking {
        client.searchAutoComplete("ark").let { (tags) ->
            assertFalse(tags.isEmpty())
        }
    }

    @Test
    fun `bookmark ranges illust`(): Unit = runBlocking {
        client.searchBookmarkRangesIllust("巨乳", offset = 0).let { (ranges) ->
            assertFalse(ranges.isEmpty())
        }
    }

    @Test
    fun user(): Unit = runBlocking {
        client.searchUser("as109").let { (previews) ->
            assertFalse(previews.isEmpty())
        }
    }
}
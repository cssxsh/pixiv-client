package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.*

internal class SearchKtTest : ApiTest() {

    @Test
    fun searchIllust(): Unit = runBlocking {
        client.searchIllust("nmsl").illusts.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun searchAutoComplete(): Unit = runBlocking {
        client.searchAutoComplete("ark").tags.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun searchBookmarkRangesIllust(): Unit = runBlocking {
        client.searchBookmarkRangesIllust("巨乳", offset = 0).ranges.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun searchUser(): Unit = runBlocking {
        client.searchUser("as109").previews.let {
            assertTrue(it.isNotEmpty())
        }
    }
}
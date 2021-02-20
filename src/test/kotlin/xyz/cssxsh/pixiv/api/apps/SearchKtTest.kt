package xyz.cssxsh.pixiv.api.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import xyz.cssxsh.pixiv.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SearchKtTest : ApiTest() {

    @Test
    fun searchIllust(): Unit = runBlocking {
        val data = client.searchIllust("nmsl")
        assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun searchNovel(): Unit = runBlocking {
        val data = client.searchNovel("e")
        assertTrue(data.novels.isNotEmpty())
    }

    @Test
    fun searchAutoComplete(): Unit = runBlocking {
        val data = client.searchAutoComplete("ark")
        assertTrue(data.keywords.isNotEmpty())
    }
}
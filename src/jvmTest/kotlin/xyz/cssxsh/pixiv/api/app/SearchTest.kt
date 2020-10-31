package xyz.cssxsh.pixiv.api.app

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SearchTest: ApiTest() {
    @Test
    fun searchIllust(): Unit = runBlocking {
        val data = pixivClient.searchIllust("arknights")
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun searchNovel(): Unit = runBlocking {
        val data = pixivClient.searchNovel("e")
        Assertions.assertTrue(data.novels.isNotEmpty())
    }

    @Test
    fun searchAutoComplete(): Unit = runBlocking {
        val data = pixivClient.searchAutoComplete("ark")
        Assertions.assertTrue(data.keywords.isNotEmpty())
    }
}
package xyz.cssxsh.pixiv.client.api.aapi

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import xyz.cssxsh.pixiv.client.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SearchTest: ApiTest() {
    @Test
    fun getSearchIllust() = runBlocking<Unit> {
        pixivClient.getSearchIllust("arknights")
    }

    @Test
    fun getSearchNovel() = runBlocking<Unit> {
        pixivClient.getSearchNovel("明日方舟")
    }

    @Test
    fun getSearchAutoComplete() = runBlocking<Unit> {
        pixivClient.getSearchAutoComplete("ark")
    }
}
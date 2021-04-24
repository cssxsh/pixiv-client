package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.ApiTest

internal class SearchKtTest : ApiTest() {

    @Test
    fun searchIllust(): Unit = runBlocking {
        client.searchIllust("nmsl").illusts.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun searchAutoComplete(): Unit = runBlocking {
        client.searchAutoComplete("ark").keywords.let {
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
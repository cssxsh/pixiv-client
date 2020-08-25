package xyz.cssxsh.pixiv.client.api.papi

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.client.SimplePixivClient
import xyz.cssxsh.pixiv.client.exception.ApiException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PublicApiKtTest {
    private val pixivClient = SimplePixivClient("http://127.0.0.1:7890")

    @BeforeAll
    fun setUp() = runBlocking {
        // pixivClient.refresh("dmQyznswcjxsZp4oTTMTluQZNtLtX4HPaWSFGiQrAOY")
        pixivClient.login("2575831437", "13087978089")
        pixivClient.checkLogin()
    }

    @Test
    fun getRanking() = runBlocking {
        assertEquals(pixivClient.getRanking("daily").infos.first().works.size, 30)
    }

    @Test
    fun getFollowing() = runBlocking {
        assertEquals(pixivClient.getFollowing().works.size, 30)
    }

    @Test
    fun getFriendsWorks() = runBlocking {
        assertEquals(pixivClient.getFriendsWorks().pagination.perPageNum, 30)
    }

    @Test
    fun getWorks() = runBlocking {
        assertEquals(pixivClient.getWorks().works.size, 30)
    }

    @Test
    fun getSearch() = runBlocking<Unit> {
        assertEquals(pixivClient.getSearchWorks("明日方舟").works.size, 30)
    }

    @Test
    fun getUserWork() = runBlocking<Unit> {
        assertEquals(pixivClient.getUserWork(93583L).works.size, 30)
    }

    @Test
    fun getUserFavorite() = assertThrows<ApiException> {
        runBlocking<Unit> {
            // XXX: 404 Not Found
            pixivClient.getUsersFavoriteWorks(37532161L)
        }
    }

    @Test
    fun getFeeds() = assertThrows<ApiException> {
        runBlocking<Unit> {
            // XXX: 404 Not Found
            pixivClient.getFeeds()
        }
    }
}
package xyz.cssxsh.pixiv.client.api.aapi

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.client.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserTest: ApiTest() {
    @Test
    fun getUserBookmarksIllust() = runBlocking {
        val data = pixivClient.getUserBookmarksIllust(3569577L)
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun getUserBookmarksNovel() = runBlocking {
        val data = pixivClient.getUserBookmarksNovel(3569577L)
        Assertions.assertTrue(data.novels.isNotEmpty())
    }

    @Test
    fun getUserRecommended() = runBlocking {
        val data =pixivClient.getUserRecommended()
        Assertions.assertTrue(data.UserPreviews.isNotEmpty())
    }

    @Test
    fun getUserFollowing() = runBlocking {
        val data = pixivClient.getUserFollowing(4102577)
        Assertions.assertTrue(data.UserPreviews.isNotEmpty())
    }

    @Test
    fun getUserFollower() = runBlocking {
        val data = pixivClient.getUserFollower(4102577)
        Assertions.assertTrue(data.UserPreviews.isNotEmpty())
    }

    @Test
    fun getUserMypixiv() = runBlocking {
        val data =pixivClient.getUserMypixiv(4102577)
        Assertions.assertTrue(data.UserPreviews.isNotEmpty())
    }

    @Test
    fun getUserIllusts() = runBlocking {
        val data = pixivClient.getUserIllusts(3569577L)
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }
}
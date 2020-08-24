package xyz.cssxsh.pixiv.client.api.aapi

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import xyz.cssxsh.pixiv.client.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserTest: ApiTest() {
    @Test
    fun getUserBookmarksIllust() = runBlocking<Unit> {
        pixivClient.getUserBookmarksIllust(3569577L)
    }

    @Test
    fun getUserBookmarksNovel() = runBlocking<Unit> {
        pixivClient.getUserBookmarksNovel(3569577L)
    }

    @Test
    fun getUserRecommended() = runBlocking<Unit> {
        pixivClient.getUserRecommended()
    }

    @Test
    fun getUserFollowing() = runBlocking<Unit> {
        pixivClient.getUserFollowing(4102577)
    }

    @Test
    fun getUserFollower() = runBlocking<Unit> {
        pixivClient.getUserFollower(4102577)
    }

    @Test
    fun getUserMypixiv() = runBlocking<Unit> {
        pixivClient.getUserMypixiv(4102577)
    }

    @Test
    fun getUserIllusts() = runBlocking<Unit> {
        pixivClient.getUserIllusts(3569577L)
    }
}
package xyz.cssxsh.pixiv.api.app

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserTest: ApiTest() {
    @Test
    fun getUserBookmarksIllust() = runBlocking {
        val data = pixivClient.userBookmarksIllust(12905943L)
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun getUserBookmarksNovel() = runBlocking {
        val data = pixivClient.userBookmarksNovel(12905943L)
        Assertions.assertTrue(data.novels.isNotEmpty())
    }

    @Test
    fun userBlacklist() = runBlocking {
        val data = pixivClient.userBlacklist()
        Assertions.assertTrue(data.users.isNotEmpty())
    }

    @Test
    fun userBookmarksTagsIllust() = runBlocking {
        val data = pixivClient.userBookmarksTagsIllust()
        Assertions.assertTrue(data.bookmarkTags.isNotEmpty())
    }

    @Test
    fun userBookmarksTagsNovel() = runBlocking {
        val data = pixivClient.userBookmarksTagsNovel()
        Assertions.assertTrue(data.bookmarkTags.isNotEmpty())
    }

    @Test
    fun getUserDetail() = runBlocking {
        val data = pixivClient.userDetail(3569577L)
        Assertions.assertTrue(data.user.id == 3569577L)
    }

    @Test
    fun getUserRecommended() = runBlocking {
        val data =pixivClient.userRecommended()
        Assertions.assertTrue(data.UserPreviews.isNotEmpty())
    }

    @Test
    fun getUserFollowing() = runBlocking {
        pixivClient.userFollowAdd(4102577)
        pixivClient.userFollowing(4102577).run {
            Assertions.assertTrue(UserPreviews.isNotEmpty())
        }
        pixivClient.userFollowDelete(4102577)
        pixivClient.userFollowing(4102577).run {
            Assertions.assertTrue(UserPreviews.isNotEmpty())
        }
    }

    @Test
    fun getUserFollower() = runBlocking {
        val data = pixivClient.userFollower(4102577)
        Assertions.assertTrue(data.UserPreviews.isNotEmpty())
    }

    @Test
    fun getUserMyPixiv() = runBlocking {
        val data =pixivClient.userMyPixiv(4102577)
        Assertions.assertTrue(data.UserPreviews.isNotEmpty())
    }

    @Test
    fun getUserIllusts() = runBlocking {
        val data = pixivClient.userIllusts(3569577L)
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }
}
package xyz.cssxsh.pixiv.api.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserTest : ApiTest() {
    @Test
    fun getUserBookmarksIllust(): Unit = runBlocking {
        val data = client.userBookmarksIllust(12905943L)
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun getUserBookmarksNovel(): Unit = runBlocking {
        val data = client.userBookmarksNovel(12905943L)
        Assertions.assertTrue(data.novels.isNotEmpty())
    }

    @Test
    fun userBlacklist(): Unit = runBlocking {
        val data = client.userBlacklist()
        Assertions.assertTrue(data.users.isNotEmpty())
    }

    @Test
    fun userBookmarksTagsIllust(): Unit = runBlocking {
        val data = client.userBookmarksTagsIllust()
        Assertions.assertTrue(data.bookmarkTags.isNotEmpty())
    }

    @Test
    fun userBookmarksTagsNovel(): Unit = runBlocking {
        val data = client.userBookmarksTagsNovel()
        Assertions.assertTrue(data.bookmarkTags.isNotEmpty())
    }

    @Test
    fun getUserDetail(): Unit = runBlocking {
        val data = client.userDetail(3569577L)
        Assertions.assertTrue(data.user.id == 3569577L)
    }

    @Test
    fun getUserRecommended(): Unit = runBlocking {
        val data = client.userRecommended()
        Assertions.assertTrue(data.userPreviews.isNotEmpty())
    }

    @Test
    fun getUserFollowing(): Unit = runBlocking {
        client.userFollowAdd(4102577)
        client.userFollowing(4102577).run {
            Assertions.assertTrue(userPreviews.isNotEmpty())
        }
        client.userFollowDelete(4102577)
        client.userFollowing(4102577).run {
            Assertions.assertTrue(userPreviews.isNotEmpty())
        }
    }

    @Test
    fun getUserFollower(): Unit = runBlocking {
        val data = client.userFollower(4102577)
        Assertions.assertTrue(data.userPreviews.isNotEmpty())
    }

    @Test
    fun getUserMyPixiv(): Unit = runBlocking {
        val data = client.userMyPixiv(4102577)
        Assertions.assertTrue(data.userPreviews.isNotEmpty())
    }

    @Test
    fun getUserIllusts(): Unit = runBlocking {
        val data = client.userIllusts(3569577L)
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }
}
package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UserKtTest : ApiTest() {
    @Test
    fun getUserBookmarksIllust(): Unit = runBlocking {
        client.userBookmarksIllust(12905943L).illusts.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun getUserBookmarksNovel(): Unit = runBlocking {
        client.userBookmarksNovel(12905943L).novels.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun userBlacklist(): Unit = runBlocking {
        client.userBlacklist().users.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun userBookmarksTagsIllust(): Unit = runBlocking {
        client.userBookmarksTagsIllust().tags.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun userBookmarksTagsNovel(): Unit = runBlocking {
        client.userBookmarksTagsNovel().tags.let {
            assertTrue(it.isNotEmpty())
        }
    }


    @Test
    fun getUserDetail(): Unit = runBlocking {
        val uid = 3569577L
        client.userDetail(uid = uid).user.let {
            assertEquals(uid, it.id)
        }
    }

    @Test
    fun getUserRecommended(): Unit = runBlocking {
        client.userRecommended().previews.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun getUserFollowing(): Unit = runBlocking {
        val uid = 4102577L
        client.userFollowAdd(uid = uid)
        client.userFollowing(uid = uid).previews.let {
            assertEquals(uid, it.first().user.id)
        }
        client.userFollowDelete(uid = uid)
        client.userFollowing(uid = uid).previews.let {
            assertNotEquals(uid, it.first().user.id)
        }
    }

    @Test
    fun getUserFollower(): Unit = runBlocking {
        val uid = 4102577L
        client.userFollower(uid = uid).previews.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun getUserMyPixiv(): Unit = runBlocking {
        val uid = 4102577L
        client.userMyPixiv(uid = uid).previews.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun getUserIllusts(): Unit = runBlocking {
        val uid = 3569577L
        client.userIllusts(uid = uid).illusts.let {
            assertTrue(it.isNotEmpty())
        }
    }
}
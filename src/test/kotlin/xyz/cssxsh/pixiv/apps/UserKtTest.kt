package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

internal class UserKtTest : AppApiKtTest() {

    @Test
    fun `bookmarks illust`(): Unit = runBlocking {
        client.userBookmarksIllust(uid = 12905943L).let { (illusts) ->
            assertFalse(illusts.isEmpty())
        }
    }

    fun blacklist(): Unit = runBlocking {
        client.userBlacklist(uid = 3410615L).let { (users) ->
            assertFalse(users.isEmpty())
        }
    }

    @Test
    fun `bookmarks tags illust`(): Unit = runBlocking {
        client.userBookmarksTagsIllust().let { (tags) ->
            assertFalse(tags.isEmpty())
        }
    }

    @Test
    fun detail(): Unit = runBlocking {
        val uid = 3569577L
        client.userDetail(uid = 3569577L).let { (_, _, user) ->
            assertEquals(uid, user.id)
        }
    }

    @Test
    fun recommended(): Unit = runBlocking {
        client.userRecommended().let { (previews) ->
            assertFalse(previews.isEmpty())
        }
    }

    fun following(): Unit = runBlocking {
        val uid = 11L
        client.userFollowDelete(uid = uid)
        client.userFollowAdd(uid = uid)
        client.userFollowing(uid = uid).let { (previews) ->
            assertEquals(uid, previews.first().user.id)
        }
        client.userFollowDelete(uid = uid)
        client.userFollowing(uid = uid).let { (previews) ->
            assertNotEquals(uid, previews.first().user.id)
        }
    }

    fun follower(): Unit = runBlocking {
        val uid = 4102577L
        client.userFollower(uid = uid).let { (previews) ->
            assertFalse(previews.isEmpty())
        }
    }

    fun friend(): Unit = runBlocking {
        val uid = 4102577L
        client.userMyPixiv(uid = uid).let { (previews) ->
            assertFalse(previews.isEmpty())
        }
    }

    @Test
    fun illusts(): Unit = runBlocking {
        client.userIllusts(uid = 11).let { (user, illusts) ->
            assertEquals(11, user.id)
            assertFalse(illusts.isEmpty())
        }
    }
}
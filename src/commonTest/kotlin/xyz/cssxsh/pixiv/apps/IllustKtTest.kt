package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import xyz.cssxsh.pixiv.RankMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class IllustKtTest : AppApiKtTest() {

    @Test
    fun bookmark(): Unit = runBlocking {
        val pid = 52594107L
        val tag = "ルオ・テンイ"
        client.illustBookmarkAdd(pid = pid, tags = setOf(tag))
        client.illustBookmarkDetail(pid = pid).let { (detail) ->
            assertTrue(detail.tags.any { it.name == tag })
            assertTrue(detail.isBookmarked)
        }
        client.illustBookmarkDelete(pid = pid)
        client.illustBookmarkDetail(pid = pid).let { (detail) ->
            assertFalse(detail.isBookmarked)
        }
    }

    @Test
    fun comments(): Unit = runBlocking {
        client.illustComments(pid = 52594107).let { (comments) ->
            assertFalse(comments.isEmpty())
        }
    }

    @Test
    fun detail(): Unit = runBlocking {
        val pid = 83919385L
        client.illustDetail(pid = pid).let { (illust) ->
            assertEquals(pid, illust.pid)
        }
    }

    @Test
    fun follow(): Unit = runBlocking {
        client.illustFollow().let { (illusts) ->
            assertFalse(illusts.isEmpty())
            assertEquals(PAGE_SIZE, illusts.size.toLong())
        }
        client.illustFollow(offset = PAGE_SIZE).let { (illusts) ->
            assertFalse(illusts.isEmpty())
        }
        client.illustFollow(offset = FOLLOW_LIMIT).let { (illusts) ->
            assertTrue(illusts.isEmpty())
        }
    }

    @Test
    fun friend(): Unit = runBlocking {
        client.illustMyPixiv().illusts
        client.illustMyPixiv(offset = PAGE_SIZE).illusts
    }

    @Test
    fun new(): Unit = runBlocking {
        client.illustNew().let { (illusts) ->
            assertFalse(illusts.isEmpty())
            assertEquals(PAGE_SIZE, illusts.size.toLong())
        }
    }

    @Test
    fun ranking(): Unit = runBlocking {
        client.illustRanking().let { (illusts) ->
            assertFalse(illusts.isEmpty())
        }
        val date = LocalDate(2020, 8, 20)
        client.illustRanking(mode = RankMode.WEEK, date = date, offset = PAGE_SIZE).let { (illusts) ->
            assertFalse(illusts.isEmpty())
        }
    }

    @Test
    fun recommended(): Unit = runBlocking {
        client.illustRecommended().let { (illusts, ranking) ->
            assertFalse(illusts.isEmpty())
            if (ranking.isEmpty().not()) {
                assertEquals(RECOMMENDED_PAGE_SIZE, (illusts.size + ranking.size).toLong())
            }
        }
    }

    @Test
    fun related(): Unit = runBlocking {
        client.illustRelated(pid = 52594107).let { (illusts) ->
            assertFalse(illusts.isEmpty())
            assertEquals(PAGE_SIZE, illusts.size.toLong())
        }
    }

    @Test
    fun timeanddate() = runBlocking {
        println("Hello world")
    }

}
package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.ApiTest
import xyz.cssxsh.pixiv.RankMode
import java.time.LocalDate

internal class IllustKtTest : ApiTest() {

    @Test
    fun illustBookmark(): Unit = runBlocking {
        val pid = 52594107L
        val tag = "ルオ・テンイ"
        client.illustBookmarkAdd(pid = pid, tags = setOf(tag))
        client.illustBookmarkDetail(pid = pid).detail.let { detail ->
            assertEquals(pid, detail.tags.any { it.name == tag })
            assertTrue(detail.isBookmarked)
        }
        client.illustBookmarkDelete(pid = pid)
        client.illustBookmarkDetail(pid = pid).detail.let { detail ->
            assertFalse(detail.isBookmarked)
        }
    }

    @Test
    fun illustComments(): Unit = runBlocking {
        client.illustComments(pid = 52594107).comments.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun illustDetail(): Unit = runBlocking {
        val pid = 83919385L
        client.illustDetail(pid = pid).illust.let {
            assertEquals(pid, it.pid)
        }
    }

    @Test
    fun illustFollow(): Unit = runBlocking {
        client.illustFollow().illusts.let {
            assertTrue(it.isNotEmpty())
        }
        client.illustFollow(offset = 30).illusts.let {
            assertTrue(it.isNotEmpty())
        }
        client.illustFollow(offset = FOLLOW_LIMIT).illusts.let {
            assertTrue(it.isEmpty())
        }
    }

    @Test
    fun illustMyPixiv(): Unit = runBlocking {
        client.illustMyPixiv().illusts.let {
            assertTrue(it.isNotEmpty())
        }
        client.illustMyPixiv(offset = 30).illusts.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun illustNew(): Unit = runBlocking {
        client.illustNew().illusts.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun illustRanking(): Unit = runBlocking {
        client.illustRanking().illusts.let {
            assertTrue(it.isNotEmpty())
        }
        client.illustRanking(mode = RankMode.MONTH, date = LocalDate.of(2020, 8, 20)).illusts.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun illustRecommended(): Unit = runBlocking {
        client.illustRecommended().illusts.let {
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun illustRelated(): Unit = runBlocking {
        client.illustRelated(pid = 52594107).illusts.let {
            assertTrue(it.isNotEmpty())
        }
    }
}
package xyz.cssxsh.pixiv.api.apps

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.api.*
import java.time.LocalDate

internal class IllustKtTest : ApiTest() {

    @Suppress("unused")
    fun illustBookmarkAdd(): Unit = runBlocking {
        val data = client.illustBookmarkAdd(52594107, listOf("ルオ・テンイ"))
        assertFalse(data is JsonNull)
    }

    @Suppress("unused")
    fun illustBookmarkDelete(): Unit = runBlocking {
        val data = client.illustBookmarkDelete(52594107)
        assertFalse(data is JsonNull)
    }

    @Test
    fun illustBookmarkDetail(): Unit = runBlocking {
        val data = client.illustBookmarkDetail(83919385)
        assertFalse(data.bookmarkDetail.isBookmarked)
    }

    @Test
    fun illustComments(): Unit = runBlocking {
        val data = client.illustComments(52594107)
        assertTrue(data.comments.isNotEmpty())
    }

    @Test
    fun illustDetail(): Unit = runBlocking {
        val data = client.illustDetail(83919385)
        assertTrue(data.illust.pid == 83919385L)
    }

    @Test
    fun illustFollow(): Unit = runBlocking {
        val data = client.illustFollow()
        assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun illustMyPixiv(): Unit = runBlocking {
        val illust = client.illustMyPixiv()
        assertTrue(illust.illusts.isEmpty())
        val manga = client.illustMyPixiv(type = WorkContentType.MANGA)
        assertTrue(manga.illusts.isNotEmpty())
    }

    @Test
    fun illustRanking(): Unit = runBlocking {
        val data = client.illustRanking(date = LocalDate.of(2020, 8, 20))
        assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun illustRecommended(): Unit = runBlocking {
        val data = client.illustRecommended()
        assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun illustRelated(): Unit = runBlocking {
        val data = client.illustRelated(52594107, listOf(52594107))
        assertTrue(data.illusts.isNotEmpty())
    }
}
package xyz.cssxsh.pixiv.api.app

import com.soywiz.klock.wrapped.WDate
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Assertions
import xyz.cssxsh.pixiv.WorkContentType
import xyz.cssxsh.pixiv.api.ApiTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class IllustTest: ApiTest() {

    @Suppress("unused")
    fun addIllustBookmark(): Unit = runBlocking {
        val data = pixivClient.illustBookmarkAdd(52594107, listOf("ルオ・テンイ"))
        Assertions.assertFalse(data is JsonNull)
    }

    @Suppress("unused")
    fun deleteIllustBookmark(): Unit = runBlocking {
        val data = pixivClient.illustBookmarkDelete(52594107)
        Assertions.assertFalse(data is JsonNull)
    }

    @Test
    fun getIllustBookmarkDetail(): Unit = runBlocking {
        val data = pixivClient.illustBookmarkDetail(83919385)
        Assertions.assertFalse(data.bookmarkDetail.isBookmarked)
    }

    @Test
    fun getIllustComments(): Unit = runBlocking {
        val data = pixivClient.illustComments(52594107)
        Assertions.assertTrue(data.comments.isNotEmpty())
    }

    @Test
    fun getIllustDetail(): Unit = runBlocking {
        val data = pixivClient.illustDetail(83919385)
        Assertions.assertTrue(data.illust.pid == 83919385L)
    }

    @Test
    fun getIllustFollow(): Unit = runBlocking {
        val data = pixivClient.illustFollow()
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun getIllustMyPixiv(): Unit = runBlocking {
        val data = pixivClient.illustMyPixiv()
        Assertions.assertTrue(data.illusts.isEmpty())
    }

    @Test
    fun getIllustNew(): Unit = runBlocking {
        val data = pixivClient.illustNew(workContentType = WorkContentType.MANGA)
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun getIllustRanking(): Unit = runBlocking {
        val data = pixivClient.illustRanking(date = WDate(year = 2020, month = 8 ,day = 20))
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun getIllustRecommended(): Unit = runBlocking {
        val data = pixivClient.illustRecommended()
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }

    @Test
    fun getIllustRelated(): Unit = runBlocking {
        val data = pixivClient.illustRelated(52594107, listOf(52594107))
        Assertions.assertTrue(data.illusts.isNotEmpty())
    }
}
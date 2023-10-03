package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class TrendingKtTest : AppApiKtTest() {

    @Test
    fun `trending tags illust`(): Unit = runBlocking {
        client.trendingTagsIllust().let { (trends) ->
            assertFalse(trends.isEmpty())
            trends.forEach { illust ->
                assertTrue(illust.tag.isNotEmpty())
            }
        }
    }
}
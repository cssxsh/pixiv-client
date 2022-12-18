package xyz.cssxsh.pixiv.apps

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
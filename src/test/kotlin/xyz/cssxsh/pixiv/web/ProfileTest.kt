package xyz.cssxsh.pixiv.web

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import xyz.cssxsh.pixiv.*

internal class ProfileTest : WebTest() {

    @Test
    fun all(): Unit = runBlocking {
        client.getProfileAll(uid = 11).let {
            println(it.illusts.size)
        }
    }
}
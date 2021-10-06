package xyz.cssxsh.pixiv.web

import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import xyz.cssxsh.pixiv.*

internal class UserApiKtTest : WebTest() {

    @Test
    fun ajaxProfileAll(): Unit = runBlocking {
        client.ajaxProfileAll(uid = 12905943).let {
            println(it.illusts.size)
        }
    }

    @Test
    fun ajaxProfileTop(): Unit = runBlocking {
        client.ajaxProfileTop(uid = 12905943).let {
            println(it.illusts.size)
        }
    }

    @Test
    fun ajaxProfileIllusts(): Unit = runBlocking {
        //
    }

    @Test
    fun ajaxFollowing(): Unit = runBlocking {
        //
    }

    @Test
    fun ajaxBookmarks(): Unit = runBlocking {
        //
    }
}
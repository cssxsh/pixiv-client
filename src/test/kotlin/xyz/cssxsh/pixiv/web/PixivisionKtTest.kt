package xyz.cssxsh.pixiv.web

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import xyz.cssxsh.pixiv.WebTest

import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.apps.spotlightArticles

internal class PixivisionKtTest : WebTest() {

    @Test
    fun getArticle() = runBlocking {
        client.spotlightArticles().articles.forEach { data ->
            println(data.id)
            client.getArticle(aid = data.id).let {
                println(it.title)
                println(it.description)
            }
        }
    }
}
package xyz.cssxsh.pixiv.publics

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.ApiTest

internal class WorksKtTest : ApiTest() {

    private val list: List<Long> = listOf(
        2117
    )

    @Test
    fun getWorks(): Unit = runBlocking {
        client.getWorks().works.forEach {
            println(it.sanityLevel)
        }
    }

    @Test
    fun getWork(): Unit = runBlocking {
        list.forEach { pid ->
            client.getWork(pid).works.single().let {
                println(it.ageLimit)
            }
        }
    }
}
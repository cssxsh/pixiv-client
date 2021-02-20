package xyz.cssxsh.pixiv.api

import xyz.cssxsh.pixiv.client.SimplePixivClient

abstract class ApiTest {
    val client = SimplePixivClient {
        account {
            mailOrUID = "1438159989@qq.com"
            password = "60SIFfeTjY"
        }
    }
}
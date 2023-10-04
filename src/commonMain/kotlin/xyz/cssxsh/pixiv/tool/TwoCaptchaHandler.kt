package xyz.cssxsh.pixiv.tool

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*

/**
 * [2captcha-api](https://2captcha.com/2captcha-api#solving_recaptchav2_new)
 */
public class TwoCaptchaHandler(private val clientKey: String) : CaptchaHandler {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(PixivJson)
        }
        ContentEncoding()
    }

    @Serializable
    public data class Result(
        @SerialName("status")
        val status: Int,
        @SerialName("request")
        val request: String,
        @SerialName("error_text")
        val errorText: String? = null
    )

    private suspend fun start(siteKey: String, referer: String): Long {
        val result: Result = client.get("https://2captcha.com/in.php") {
            parameter("key", clientKey)
            parameter("method", "userrecaptcha")
            parameter("enterprise", 1)
            parameter("googlekey", siteKey)
            parameter("pageurl", referer)
            parameter("domain", "recaptcha.net")
            parameter("invisible", 1)
            parameter("json", 1)
            parameter("lang", "zh")
        }.body()
        check(result.status == 1) { result }

        return result.request.toLong()
    }

    private suspend fun check(id: Long): String {
        val result: Result = client.get("https://2captcha.com/res.php") {
            parameter("key", clientKey)
            parameter("action", "get")
            parameter("id", id)
            parameter("json", 1)
            parameter("lang", "zh")
        }.body()
        check(result.status == 1) { result }

        return result.request
    }

    override suspend fun handle(siteKey: String, referer: String): String {
        val id = start(siteKey = siteKey, referer = referer)
        delay(20_000)
        while (true) {
            try {
                return check(id = id)
            } catch (_: IllegalStateException) {
                delay(5_000)
            }
        }
    }
}
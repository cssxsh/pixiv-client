package xyz.cssxsh.pixiv.tool

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.*

/**
 * [wiki](https://yescaptcha.atlassian.net/wiki/home)
 *
 * @param host 国内: api.yescaptcha.com 香港: hk.yescaptcha.com
 */
public class YesCaptchaHandler(private val clientKey: String, public val host: String = "api.yescaptcha.com") :
    CaptchaHandler {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(PixivJson)
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }

    /**
     * [全部任务类型及价格](https://yescaptcha.atlassian.net/wiki/spaces/YESCAPTCHA/pages/164286)
     */
    private val type: String = "RecaptchaV2EnterpriseTaskProxylessM1"

    /**
     * [查询账户余额](https://yescaptcha.atlassian.net/wiki/spaces/YESCAPTCHA/pages/229767/getBalance)
     * @return 余额
     */
    public suspend fun getBalance(): Double {
        val result: JsonObject = client.post("https://$host/getBalance") {
            setBody(buildJsonObject {
                put("clientKey", clientKey)
            })
        }.body()

        return requireNotNull(result["balance"]?.jsonPrimitive?.doubleOrNull) { result }
    }

    /**
     * 1. 每个任务限制最多120 次请求
     * 2. 每个任务创建后 5 分钟内可以查询
     *
     * [获取识别结果](https://yescaptcha.atlassian.net/wiki/spaces/YESCAPTCHA/pages/196857/getTaskResult)
     *
     * @return 识别结果
     */
    private suspend fun getTaskResult(taskId: String): Pair<Boolean, JsonObject> {
        val result: JsonObject = client.post("https://$host/getTaskResult") {
            setBody(buildJsonObject {
                put("clientKey", clientKey)
                put("taskId", taskId)
            })
        }.body()

        val status = requireNotNull(result["status"]?.jsonPrimitive?.contentOrNull) { result }

        return (status == "ready") to (result["solution"] as? JsonObject ?: JsonObject(emptyMap()))
    }

    /**
     * [创建识别任务](https://yescaptcha.atlassian.net/wiki/spaces/YESCAPTCHA/pages/33351/createTask)
     * [所有验证码类型](https://yescaptcha.atlassian.net/wiki/spaces/YESCAPTCHA/overview#%E9%AA%8C%E8%AF%81%E7%A0%81%E4%BB%BB%E5%8A%A1%E7%B1%BB%E5%9E%8B)
     *
     * @return TaskId
     */
    private suspend fun createTask(builderTask: JsonObjectBuilder.() -> Unit): String {
        val result: JsonObject = client.post("https://$host/createTask") {
            setBody(buildJsonObject {
                put("clientKey", clientKey)
                putJsonObject("task", builderTask)
            })
        }.body()

        return requireNotNull(result["taskId"]?.jsonPrimitive?.contentOrNull) { result }
    }

    override suspend fun handle(siteKey: String, referer: String): String {
        val taskId = createTask {
            put("websiteURL", referer)
            put("websiteKey", siteKey)
            put("type", type)
        }
        var count = 120
        while (count-- > 0) {
            val (ready, solution) = try {
                getTaskResult(taskId)
            } catch (_: ConnectTimeoutException) {
                continue
            }
            if (ready) {
                return solution.getValue("gRecaptchaResponse").jsonPrimitive.content
            }

            delay(2_500)
        }

        throw IllegalStateException("识别超时")
    }
}
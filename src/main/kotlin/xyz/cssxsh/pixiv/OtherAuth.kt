package xyz.cssxsh.pixiv

import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import xyz.cssxsh.pixiv.auth.*

const val WEIBO_QRCODE_GENERATE = "https://api.weibo.com/oauth2/qrcode_authorize/generate"

const val WEIBO_QRCODE_QUERY = "https://api.weibo.com/oauth2/qrcode_authorize/query"

private fun HttpMessage.location() = headers[HttpHeaders.Location]?.let(::Url)

@Serializable
internal data class HtmlAccount(
    @SerialName("pixivAccount.continueWithCurrentAccountUrl")
    val current: String = "",
    @SerialName("pixivAccount.returnTo")
    val returnTo: String,
    @SerialName("pixivAccount.tt")
    val tt: String,
    @SerialName("pixivAccount.userId")
    val uid: Long? = null,
)

private suspend fun HttpResponse.account(): HtmlAccount {
    val html = receive<String>()
    val data = html.substringAfter("value='").substringBefore("'")
    return PixivJson.decodeFromString(data)
}

/**
 * link for [POST_REDIRECT_URL]
 */
private suspend fun PixivAuthClient.redirect(link: Url): String {
    val redirect: HttpResponse = useHttpClient { it.get(link) }
    val location = redirect.request.url

    check(location.toString().startsWith(POST_REDIRECT_URL)) { "$link To $location" }

    val account = redirect.account()

    checkNotNull(account.uid) { "未登录" }

    val current = Url(account.returnTo)

    /**
     * current for [START_URL]
     */
    val response: HttpMessage = useHttpClient {
        it.post(current) {
            expectSuccess = false

            header(HttpHeaders.Origin, ORIGIN_URL)
            header(HttpHeaders.Referrer, LOGIN_URL)
        }
    }

    val authorize = requireNotNull(response.location()) { "跳转到 $OAUTH_AUTHORIZE_URL 失败" }

    /**
     * authorize for [OAUTH_AUTHORIZE_URL]
     */
    val code: HttpMessage = useHttpClient {
        it.get(authorize) {
            expectSuccess = false

            header(HttpHeaders.Origin, ORIGIN_URL)
            header(HttpHeaders.Referrer, LOGIN_URL)
        }
    }

    /**
     * Code by Url pixiv://...
     */
    return requireNotNull(code.location()) { "跳转到 pixiv://... 失败" }.parameters["code"]!!
}

/**
 * 登录，通过新浪微博关联Pixiv
 */
suspend fun PixivAuthClient.sina(show: suspend (Url) -> Unit) = login { url ->
    // Pixiv Login Page
    val html: String = useHttpClient { it.get(url) }

    val all = html.let("""gigya-auth[^"]+""".toRegex()::findAll).map { it.value.replace("&amp;", "&") }
    val sina = Url("$ORIGIN_URL/${all.first { "sina" in it }}")

    // Jump to Sina Weibo
    val temp: HttpResponse = useHttpClient { it.head(sina) }
    // 为了直接继承参数 ↓
    val generate = Url(WEIBO_QRCODE_GENERATE).copy(parameters = temp.request.url.parameters)
    val qrcode: WeiboQrcode = PixivJson.decodeFromString(useHttpClient { it.get(generate) })

    supervisorScope {
        show(Url(qrcode.url))
    }

    lateinit var jump: String
    withTimeout(10 * 60 * 1000L) {
        while (isActive) {
            delay(3 * 1000L)
            val status: WeiboQrcodeStatus = PixivJson.decodeFromString(useHttpClient {
                it.get(WEIBO_QRCODE_QUERY) {
                    parameter("vcode", qrcode.vcode)
                }
            })
            jump = status.url ?: continue
            break
        }
    }

    // replace protocol for ssl with g-client-proxy.pixiv.net
    val gigya: String = useHttpClient {
        it.get(Url(jump).copy(protocol = URLProtocol.HTTPS))
    }

    val sign = gigya.substringAfter("redirect('").substringBefore("');")
    // transform Unicode
    val link = Url(PixivJson.decodeFromString<String>(sign))

    /**
     * GiGya auto to [POST_REDIRECT_URL]
     */
    redirect(link = link)
}

/**
 * 登录，通过 Web Cookies
 */
suspend fun PixivAuthClient.cookie(load: () -> List<Cookie>) = login { url ->
    storage.save(load())
    val login: HttpResponse = useHttpClient { it.get(url) }
    val account = login.account()

    /**
     * for [POST_SELECTED_URL]
     */
    val response: HttpMessage = useHttpClient {
        it.post(POST_SELECTED_URL) {
            expectSuccess = false

            header(HttpHeaders.Origin, ORIGIN_URL)
            header(HttpHeaders.Referrer, LOGIN_URL)

            body = FormDataContent(Parameters.build {
                append("return_to", account.current)
                append("tt", account.tt)
            })
        }
    }

    val link = requireNotNull(response.location()) { "跳转到 $POST_REDIRECT_URL 失败" }

    redirect(link = link)
}
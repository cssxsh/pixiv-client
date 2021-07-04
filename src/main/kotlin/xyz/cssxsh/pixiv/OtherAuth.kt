package xyz.cssxsh.pixiv

import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import xyz.cssxsh.pixiv.auth.*

const val WEIBO_QRCODE_GENERATE = "https://api.weibo.com/oauth2/qrcode_authorize/generate"

const val WEIBO_QRCODE_QUERY = "https://api.weibo.com/oauth2/qrcode_authorize/query"

internal fun HttpMessage.location() = headers[HttpHeaders.Location]?.let { Url(it) }

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
    return PixivJson.decodeFromString(html.substringAfter("value='").substringBefore("'"))
}

/**
 * link for [POST_REDIRECT_URL]
 */
private suspend fun PixivAuthClient.redirect(link: Url): String {
    val redirect: HttpResponse = useHttpClient { it.get(link) }

    check(redirect.request.url.toString().startsWith(POST_REDIRECT_URL)) { redirect.request.url.toString() }

    val account = redirect.account()

    checkNotNull(account.uid) { "未登录" }

    val current = Url(account.returnTo)

    // println(current)

    /**
     * current for [START_URL]
     */
    val response: HttpResponse = useHttpClient {
        it.post(current) {
            expectSuccess = false

            header(HttpHeaders.Origin, "https://accounts.pixiv.net")
            header(HttpHeaders.Referrer, "https://accounts.pixiv.net/")
        }
    }

    val authorize = requireNotNull(response.location())

    // println(authorize)

    /**
     * authorize for [OAUTH_AUTHORIZE_URL]
     */
    val code: HttpResponse = useHttpClient {
        it.get(authorize) {
            expectSuccess = false

            header(HttpHeaders.Origin, "https://accounts.pixiv.net")
            header(HttpHeaders.Referrer, "https://accounts.pixiv.net/")
        }
    }

    /**
     * Code by Url pixiv://...
      */
    return Url(code.headers[HttpHeaders.Location]!!).parameters["code"]!!
}

/**
 * 登录，通过新浪微博关联Pixiv
 */
suspend fun PixivAuthClient.sina(show: suspend (Url) -> Unit) = login { url ->
    // Pixiv Login Page
    val html: String = useHttpClient { it.get(url) }

    val all = html.let("""gigya-auth[^"]+""".toRegex()::findAll).map { it.value.replace("&amp;", "&") }
    val sina = Url("https://accounts.pixiv.net/${all.first { "sina" in it }}")

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
            val status = PixivJson.decodeFromString<WeiboQrcodeStatus>(useHttpClient {
                it.get(WEIBO_QRCODE_QUERY) {
                    parameter("vcode", qrcode.vcode)
                }
            })
            jump = status.url ?: continue
            break
        }
    }

    // replace protocol and host for ssl
    val gigya: String = useHttpClient {
        it.get(Url(jump).copy(protocol = URLProtocol.HTTPS, host = "d1ctzrip8l97jt.cloudfront.net"))
    }

    val sign = gigya.substringAfter("redirect('").substringBefore("');")
    // transform Unicode
    val link = Url(PixivJson.decodeFromString<String>("\"${sign}\""))

    /**
     * GiGya auto to [POST_REDIRECT_URL]
     */
    redirect(link = link)
}

/**
 * 登录，通过 Web Cookies
 */
suspend fun PixivAuthClient.cookie(load: () -> List<Cookie>) = login { url ->
    cookiesStorage.save(load())
    // println(url)
    val login: HttpResponse = useHttpClient { it.get(url) }
    // check("" in login.request.url.parameters)
    val account = login.account()

    /**
     * for [POST_SELECTED_URL]
     */
    val response: HttpResponse = useHttpClient {
        it.post(POST_SELECTED_URL) {
            expectSuccess = false

            header(HttpHeaders.Origin, "https://accounts.pixiv.net")
            header(HttpHeaders.Referrer, "https://accounts.pixiv.net/")

            body = FormDataContent(Parameters.build {
                append("return_to", account.current)
                append("tt", account.tt)
            })
        }
    }

    val link = requireNotNull(response.location())

    redirect(link = link)
}
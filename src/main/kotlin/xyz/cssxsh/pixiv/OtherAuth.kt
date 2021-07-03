package xyz.cssxsh.pixiv

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import xyz.cssxsh.pixiv.auth.WeiboQrcode
import xyz.cssxsh.pixiv.auth.WeiboQrcodeStatus
import java.io.InputStream

const val WEIBO_QRCODE_GENERATE = "https://api.weibo.com/oauth2/qrcode_authorize/generate"

const val WEIBO_QRCODE_QUERY = "https://api.weibo.com/oauth2/qrcode_authorize/query"

private suspend fun PixivAuthClient.redirect(link: Url): String {
    // Pixiv Login Page
    val html: String = useHttpClient {
        it.get(link) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)
        }
    }

    val account: JsonObject = PixivJson.decodeFromString(html.substringAfter("value='").substringBefore("'"))

    checkNotNull(account.getValue("pixivAccount.userId").jsonPrimitive.longOrNull) { "未登录" }

    val current = account.getValue("pixivAccount.returnTo").jsonPrimitive.content

    val response: HttpResponse = useHttpClient {
        it.config {
            followRedirects = false
            expectSuccess = false
        }.post(current) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)

            header(HttpHeaders.Origin, "https://accounts.pixiv.net")
            header(HttpHeaders.Referrer, "https://accounts.pixiv.net/")
        }
    }

    val authorize = requireNotNull(response.headers[HttpHeaders.Location])

    val end: HttpMessage = useHttpClient {
        it.config {
            followRedirects = false
            expectSuccess = false
        }.get(authorize) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)

            header(HttpHeaders.Origin, "https://accounts.pixiv.net")
            header(HttpHeaders.Referrer, "https://accounts.pixiv.net/")
        }
    }

    // Code
    return Url(end.headers[HttpHeaders.Location]!!).parameters["code"]!!
}

/**
 * 登录，通过新浪微博关联Pixiv
 */
suspend fun PixivAuthClient.sina(show: suspend (Url, InputStream) -> Unit) = login { url ->
    // Pixiv Login Page
    val html1: String = useHttpClient {
        it.get(url) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)
        }
    }

    val all = html1.let("""gigya-auth[^"]+""".toRegex()::findAll).map { it.value.replace("&amp;", "&") }
    val sina = Url("https://accounts.pixiv.net/${all.first { "sina" in it }}")

    // Jump to Sina Weibo
    val temp: HttpResponse = useHttpClient {
        it.head(sina) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)
        }
    }
    // 为了直接继承参数 ↓
    val generate = Url(WEIBO_QRCODE_GENERATE).copy(parameters = temp.request.url.parameters)
    val qrcode: WeiboQrcode = useHttpClient {
        PixivJson.decodeFromString(it.get(generate) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)
        })
    }
    launch {
        val image: InputStream = useHttpClient {
            it.get(generate) {
                attributes.put(PixivAccessToken.PixivAuthMark, Unit)
            }
        }
        show(Url(qrcode.url), image)
    }

    lateinit var jump: String
    while (isActive) {
        delay(3 * 1000L)
        jump = useHttpClient {
            PixivJson.decodeFromString<WeiboQrcodeStatus>(it.get(WEIBO_QRCODE_QUERY) {
                attributes.put(PixivAccessToken.PixivAuthMark, Unit)

                parameter("vcode", qrcode.vcode)
            })
        }.url ?: continue
        break
    }

    // replace protocol and host for ssl
    val gigya: String = useHttpClient {
        it.get(Url(jump).copy(protocol = URLProtocol.HTTPS, host = "d1ctzrip8l97jt.cloudfront.net")) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)

            header(HttpHeaders.Host, "g-client-proxy.pixiv.net")
        }
    }

    val sign = gigya.substringAfter("redirect('").substringBefore("');")
    // transform Unicode
    val link = Url(PixivJson.decodeFromString<String>("\"${sign}\""))

    redirect(link)
}

/**
 * 登录，通过 Web Cookies
 */
suspend fun PixivAuthClient.cookie(load: () -> List<Cookie>) = login {
    cookiesStorage.save(load())

    redirect(it)
}
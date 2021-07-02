package xyz.cssxsh.pixiv

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import xyz.cssxsh.pixiv.auth.WeiboQrcode
import xyz.cssxsh.pixiv.auth.WeiboQrcodeStatus

/**
 * 登录，通过新浪微博关联Pixiv
 */
suspend fun AuthPixivClient.sina(show: suspend (ByteArray) -> Unit) = login { url ->
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
    val generate = temp.request.url.copy(encodedPath = "oauth2/qrcode_authorize/generate")
    val qrcode: WeiboQrcode = useHttpClient {
        PixivJson.decodeFromString(it.get(generate) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)
        })
    }
    launch {
        val image: ByteArray = useHttpClient {
            it.get(generate) {
                attributes.put(PixivAccessToken.PixivAuthMark, Unit)
            }
        }
        show(image)
    }

    val query = Url("https://api.weibo.com/oauth2/qrcode_authorize/query")
    lateinit var jump: String
    while (isActive) {
        delay(3 * 1000L)
        jump = useHttpClient {
            PixivJson.decodeFromString<WeiboQrcodeStatus>(it.get(query) {
                attributes.put(PixivAccessToken.PixivAuthMark, Unit)
                parameter("vcode", qrcode.vcode)
            })
        }.url ?: continue
        break
    }

    val gigya: String = useHttpClient {
        it.get(Url(jump).copy(protocol = URLProtocol.HTTPS, host = "d1ctzrip8l97jt.cloudfront.net")) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)

            header(HttpHeaders.Host, "g-client-proxy.pixiv.net")
        }
    }

    val sign = gigya.substringAfter("redirect('").substringBefore("');")
    val link = Url(PixivJson.decodeFromString<String>("\"${sign}\""))

    val html2: String = useHttpClient {
        it.get(link) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)
        }
    }

    val account: JsonObject = PixivJson.decodeFromString(html2.substringAfter("value='").substringBefore("'"))

    val current = account.getValue("pixivAccount.returnTo").jsonPrimitive.content

    val response1: HttpMessage = useHttpClient {
        it.config {
            expectSuccess = false
        }.post(current) {
            attributes.put(PixivAccessToken.PixivAuthMark, Unit)

            header(HttpHeaders.Origin, "https://accounts.pixiv.net")
            header(HttpHeaders.Referrer, "https://accounts.pixiv.net/")
        }
    }


    val authorize = requireNotNull(response1.headers[HttpHeaders.Location])

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
    Url(end.headers[HttpHeaders.Location]!!).parameters["code"]!!
}
package xyz.cssxsh.pixiv

import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.*
import xyz.cssxsh.pixiv.auth.*
import xyz.cssxsh.pixiv.tool.CaptchaHandler

public const val WEIBO_QRCODE_GENERATE: String = "https://api.weibo.com/oauth2/qrcode_authorize/generate"

public const val WEIBO_QRCODE_QUERY: String = "https://api.weibo.com/oauth2/qrcode_authorize/query"

public const val WEIBO_QRCODE_TIMEOUT: Long = 10 * 60 * 1000L

public const val WEIBO_QRCODE_INTERVAL: Long = 3 * 1000L

private fun HttpMessage.location() = headers[HttpHeaders.Location]?.let(::Url)

private fun account(html: String): HtmlAccount {
    val data = html.substringAfter("value='").substringBefore("'")
    return PixivJson.decodeFromString(HtmlAccount.serializer(), data)
}

/**
 * @param link for [POST_REDIRECT_URL]
 */
private suspend fun PixivAuthClient.redirect(link: Url): String {
    val redirect = useHttpClient { it.get(link) }
    val location = redirect.request.url

    val account = account(html = redirect.body())

    checkNotNull(account.uid) { "未登录" }

    val current = Url(location.parameters["return_to"]?.decodeURLPart() ?: account.returnTo)

    if (location.host == "accounts.pixiv.net") {
        val selected = useHttpClient { client ->
            client.submitForm(url = POST_SELECTED_URL, formParameters = Parameters.build {
                append("tt", account.token)
                append("return_to", URLBuilder(POST_REDIRECT_URL).apply {
                    parameters.append("return_to", current.toString())
                }.buildString())
            }) {
                expectSuccess = false

                header(HttpHeaders.Origin, ORIGIN_URL)
                header(HttpHeaders.Referrer, location)
            }
        }
        selected.location()?.let { url ->
            val message = useHttpClient { client ->
                client.get(url) {

                    header(HttpHeaders.Origin, ORIGIN_URL)
                    header(HttpHeaders.Referrer, location)
                }
            }
            message.bodyAsText()
        }
    }

    /**
     * current for [START_URL]
     */
    val response = useHttpClient { client ->
        client.post(current) {
            expectSuccess = false

            parameter("via", "login")

            header(HttpHeaders.Origin, ORIGIN_URL)
            header(HttpHeaders.Referrer, LOGIN_URL)
        }
    }

    val authorize = requireNotNull(response.location()) { "跳转到 $OAUTH_AUTHORIZE_URL 失败" }

    /**
     * authorize for [OAUTH_AUTHORIZE_URL]
     */
    val code = useHttpClient { client ->
        client.get(authorize) {
            expectSuccess = false

            header(HttpHeaders.Origin, ORIGIN_URL)
            header(HttpHeaders.Referrer, LOGIN_URL)
        }
    }

    val scheme = requireNotNull(code.location()) { "跳转到 pixiv://... 失败" }

    /**
     * Code by Url pixiv://...
     */
    return scheme.parameters["code"] ?: throw NoSuchElementException("code, $scheme")
}

/**
 * 登录，通过新浪微博关联Pixiv
 * @param show handle qrcode image url
 */
public suspend fun PixivAuthClient.sina(show: suspend (qrcode: Url) -> Unit): AuthResult = login { redirect ->
    /**
     * for [LOGIN_URL]
     */
    val account = account(html = useHttpClient { it.get(redirect).body() })

    /**
     * for [GIGYA_AUTH_URL]
     */
    val response = useHttpClient { client ->
        client.submitForm(url = GIGYA_AUTH_URL, formParameters = Parameters.build {
            append("tt", account.token)
            append("mode", "signin")
            append("provider", "sina")
            append("lang", "zh")
            append("ref_sns_button", "login")
            append("return_to", redirect.toString())
            append("source", "pixiv-android")
            append("view_type", "page")
        }) {
            expectSuccess = false

            header(HttpHeaders.Origin, ORIGIN_URL)
            header(HttpHeaders.Referrer, LOGIN_URL)
        }
    }

    val socialize = requireNotNull(response.location()) { "跳转到 $SOCIALIZE_LOGIN_URL 失败" }

    // Jump to Sina Weibo
    val temp = useHttpClient { it.head(socialize) }
    // 为了直接继承参数 ↓
    val qrcode = PixivJson.decodeFromString(WeiboQrcode.serializer(), useHttpClient { client ->
        client.get {
            takeFrom(temp.request)
            url {
                encodedPath = Url(WEIBO_QRCODE_GENERATE).encodedPath
            }
        }.body()
    })

    supervisorScope {
        show(Url(qrcode.url))
    }

    val jump: String = withTimeout(WEIBO_QRCODE_TIMEOUT) {
        while (isActive) {
            delay(WEIBO_QRCODE_INTERVAL)
            val status = PixivJson.decodeFromString(WeiboQrcodeStatus.serializer(), useHttpClient { client ->
                client.get(WEIBO_QRCODE_QUERY) {
                    parameter("vcode", qrcode.vcode)
                }.body()
            })
            return@withTimeout status.url ?: continue
        }
        throw CancellationException("WEIBO_QRCODE_TIMEOUT")
    }

    // replace protocol for ssl with g-client-proxy.net
    val gigya: String = useHttpClient { client ->
        client.get(jump) {
            url {
                protocol = URLProtocol.HTTPS
                host = "d1ctzrip8l97jt.cloudfront.net"
            }
        }.body()
    }

    val sign = gigya.substringAfter("redirect('").substringBefore("');")
    // transform unicode
    val unicode = """\\u(\d{4})""".toRegex()
    val link = Url(sign.replace(unicode) { it.groupValues[1].toInt(16).toChar().toString() })

    /**
     * GiGya auto to [POST_REDIRECT_URL]
     */
    redirect(link = link)
}

/**
 * 登录，通过 Web Cookies
 * @param load get pixiv web cookie
 */
public suspend fun PixivAuthClient.cookie(load: () -> List<Cookie>): AuthResult = login { redirect ->
    val origin = Url(ORIGIN_URL)
    for (cookie in load()) {
        storage.addCookie(origin, cookie)
    }
    requireNotNull(storage.get(origin)["PHPSESSID"]) { "PHPSESSID is null" }
    val account = account(html = useHttpClient { it.get(redirect).body() })

    checkNotNull(account.uid) { "未登录" }

    /**
     * for [POST_SELECTED_URL]
     */
    val response = useHttpClient { client ->
        client.submitForm(url = POST_SELECTED_URL, formParameters = Parameters.build {
            append("return_to", account.current.ifEmpty { redirect.toString() })
            append("tt", account.token)
        }) {
            expectSuccess = false

            header(HttpHeaders.Origin, ORIGIN_URL)
            header(HttpHeaders.Referrer, LOGIN_URL)
        }
    }

    val link = requireNotNull(response.location()) { "跳转到 $POST_REDIRECT_URL 失败" }

    redirect(link = link)
}

/**
 * 登录，通过 账户密码
 * @param id Pixiv ID 或者 Email
 * @param pwd 密码
 * @param handler 人机验证处理
 */
public suspend fun PixivAuthClient.password(id: String, pwd: String, handler: CaptchaHandler): AuthResult = login { r ->
    /**
     * for [LOGIN_URL]
     */
    val login = useHttpClient { it.get(r) }
    val account = account(html = login.body())

    while (isActive) {
        /**
         * 实际上 SiteKey 是固定值 6LfF1dcZAAAAAOHQX8v16MX5SktDwmQINVD_6mBF
         */
        val gRecaptchaResponse =
            handler.handle(siteKey = account.scoreSiteKey, referer = login.request.url.toString())

        val result: WebLoginResult = useHttpClient { client ->
            client.submitForm(url = LOGIN_API_URL, formParameters = Parameters.build {
                append("password", pwd)
                append("pixiv_id", id)
                append("captcha", "")
                append("g-recaptcha-response", gRecaptchaResponse)
                append("post_key", account.postKey)
                append("source", account.source)
                append("ref", account.ref)
                append("return_to", account.returnTo)
                append("recaptcha_enterprise_score_token", gRecaptchaResponse)
                append("tt", account.token)
            }) {
                header(HttpHeaders.Origin, ORIGIN_URL)
                header(HttpHeaders.Referrer, "https://accounts.pixiv.net/")

                parameter("lang", "zh")
            }.body()
        }

        if ("captcha" in result.body.validationErrors) continue

        check(result.body.validationErrors.isNotEmpty()) { result.body }

        break
    }

    /**
     * for [POST_SELECTED_URL]
     */
    val message = useHttpClient { client ->
        client.submitForm(url = POST_SELECTED_URL, formParameters = Parameters.build {
            append("return_to", account.current)
            append("tt", account.token)
        }) {
            expectSuccess = false

            header(HttpHeaders.Origin, ORIGIN_URL)
            header(HttpHeaders.Referrer, LOGIN_URL)
        }
    }

    val link = requireNotNull(message.location()) { "跳转到 $POST_REDIRECT_URL 失败" }

    redirect(link = link)
}

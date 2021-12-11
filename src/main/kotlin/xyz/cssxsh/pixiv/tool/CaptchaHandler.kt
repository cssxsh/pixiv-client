package xyz.cssxsh.pixiv.tool

/**
 * handle ReCaptcha
 */
interface CaptchaHandler {
    suspend fun handle(siteKey: String, referer: String): String
}
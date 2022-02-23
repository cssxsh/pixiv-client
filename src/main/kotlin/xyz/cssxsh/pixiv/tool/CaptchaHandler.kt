package xyz.cssxsh.pixiv.tool

/**
 * handle ReCaptcha
 */
public interface CaptchaHandler {
    public suspend fun handle(siteKey: String, referer: String): String
}
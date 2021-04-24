package xyz.cssxsh.pixiv


interface PixivWebClient : UseHttpClient {

    val config: PixivConfig

    fun config(block: PixivConfig.() -> Unit) = config.apply(block)
}
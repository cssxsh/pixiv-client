package xyz.cssxsh.pixiv

interface UseConfig {

    val config: PixivConfig

    fun config(block: PixivConfig.() -> Unit) = config.apply(block)
}
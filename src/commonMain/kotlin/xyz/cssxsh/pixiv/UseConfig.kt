package xyz.cssxsh.pixiv

public interface UseConfig {

    public val config: PixivConfig

    public fun config(block: PixivConfig.() -> Unit): PixivConfig = config.apply(block)
}
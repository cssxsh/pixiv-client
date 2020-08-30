package xyz.cssxsh.pixiv.client

expect open class SimplePixivClient(config: PixivConfig) : PixivClient, AbstractPixivClient {
    constructor(block: PixivConfig.() -> Unit = {})
}
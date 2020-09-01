package xyz.cssxsh.pixiv.client

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

expect open class SimplePixivClient(
    parentCoroutineContext: CoroutineContext = EmptyCoroutineContext,
    config: PixivConfig
) : PixivClient, AbstractPixivClient {
    constructor(
        parentCoroutineContext: CoroutineContext = EmptyCoroutineContext,
        block: PixivConfig.() -> Unit = {}
    )
}
package xyz.cssxsh.pixiv

import io.ktor.client.features.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.SupervisorJob
import okio.IOException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class SimplePixivClient(
    parentCoroutineContext: CoroutineContext = EmptyCoroutineContext,
    coroutineName: String = "SimplePixivClient",
    override val config: PixivConfig,
) : AuthPixivClient(), PixivWebClient {

    override val coroutineContext: CoroutineContext by lazy {
        parentCoroutineContext + CoroutineName(coroutineName) + SupervisorJob()
    }

    override val ignore: suspend (Throwable) -> Boolean = { it is IOException || it is HttpRequestTimeoutException }
}
package xyz.cssxsh.pixiv

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

internal actual val httpClient: HttpClientEngineFactory<*> = OkHttp
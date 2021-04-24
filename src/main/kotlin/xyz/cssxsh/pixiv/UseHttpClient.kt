package xyz.cssxsh.pixiv

import io.ktor.client.*

interface UseHttpClient {

    suspend fun <R> useHttpClient(
        block: suspend (HttpClient) -> R,
    ): R
}
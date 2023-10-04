package xyz.cssxsh.pixiv

import io.ktor.client.*

public interface UseHttpClient {

    public suspend fun <R> useHttpClient(block: suspend (HttpClient) -> R): R
}
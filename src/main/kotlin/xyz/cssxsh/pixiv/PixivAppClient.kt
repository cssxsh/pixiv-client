package xyz.cssxsh.pixiv

import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import xyz.cssxsh.pixiv.auth.AuthResult

/**
 * PixivClient，Multiplatform interface
 */
interface PixivAppClient : UseHttpClient, UseConfig, CoroutineScope {

    suspend fun login(block: suspend (Url) -> String): AuthResult

    suspend fun refresh(): AuthResult

    suspend fun info(): AuthResult
}
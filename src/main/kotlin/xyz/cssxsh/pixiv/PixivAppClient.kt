package xyz.cssxsh.pixiv

import kotlinx.coroutines.CoroutineScope
import xyz.cssxsh.pixiv.auth.AuthResult

/**
 * PixivClient，Multiplatform interface
 */
interface PixivAppClient : UseHttpClient, UseConfig, CoroutineScope {

    suspend fun login(mailOrPixivID: String, password: String): AuthResult

    suspend fun refresh(token: String): AuthResult

    suspend fun autoAuth(): AuthResult

    suspend fun getAuthInfo(): AuthResult
}
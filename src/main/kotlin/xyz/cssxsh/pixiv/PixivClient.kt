package xyz.cssxsh.pixiv

import kotlinx.coroutines.CoroutineScope
import xyz.cssxsh.pixiv.auth.AuthResult

/**
 * PixivClient，Multiplatform interface
 */
interface PixivClient : UseHttpClient, CoroutineScope {

    val config: PixivConfig

    fun config(block: PixivConfig.() -> Unit) = config.apply(block)

    suspend fun login(mailOrPixivID: String, password: String): AuthResult

    suspend fun refresh(token: String): AuthResult

    suspend fun autoAuth(): AuthResult

    suspend fun getAuthInfo(): AuthResult
}
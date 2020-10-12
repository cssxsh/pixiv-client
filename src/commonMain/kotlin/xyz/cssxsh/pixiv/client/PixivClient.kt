package xyz.cssxsh.pixiv.client

import io.ktor.client.*
import kotlinx.coroutines.CoroutineScope
import xyz.cssxsh.pixiv.GrantType
import xyz.cssxsh.pixiv.data.AuthResult

/**
 * PixivClient，Multiplatform interface
 */
interface PixivClient : CoroutineScope {

    fun httpClient(): HttpClient

    val config: PixivConfig

    fun config(block: PixivConfig.() -> Unit) = config.apply(block)

    suspend fun login(mailOrPixivID: String, password: String): AuthResult.AuthInfo

    suspend fun refresh(token: String): AuthResult.AuthInfo

    suspend fun auth(grantType: GrantType, config: PixivConfig): AuthResult.AuthInfo

    suspend fun autoAuth(): AuthResult.AuthInfo

    suspend fun getAuthInfo(): AuthResult.AuthInfo
}
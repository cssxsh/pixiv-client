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

    val authInfo: AuthResult.AuthInfo?

    fun getAuthInfoOrThrow(): AuthResult.AuthInfo = requireNotNull(authInfo) {
        "Not Logged In For PixivClient."
    }

    fun config(block: PixivConfig.() -> Unit) = config.apply(block)

    suspend fun login(mailOrPixivID: String, password: String): AuthResult.AuthInfo

    suspend fun refresh(token: String): AuthResult.AuthInfo

    suspend fun auth(grantType: GrantType, config: PixivConfig): AuthResult.AuthInfo
}
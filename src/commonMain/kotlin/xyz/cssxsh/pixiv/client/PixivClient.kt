package xyz.cssxsh.pixiv.client

import io.ktor.client.HttpClient
import xyz.cssxsh.pixiv.GrantType
import xyz.cssxsh.pixiv.data.AuthResult

/**
 * PixivClient，Multiplatform interface
 */
interface PixivClient {

    val httpClient: HttpClient

    val config: PixivConfig

    val authInfo: AuthResult.AuthInfo

    val isLoggedIn: Boolean

    fun config(block: PixivConfig.() -> Unit) = config.apply(block)

    suspend fun login(): AuthResult.AuthInfo = auth(GrantType.PASSWORD)

    suspend fun refresh(): AuthResult.AuthInfo = auth(GrantType.REFRESH_TOKEN)

    suspend fun auth(grantType: GrantType): AuthResult.AuthInfo

    fun close() = httpClient.close()
}
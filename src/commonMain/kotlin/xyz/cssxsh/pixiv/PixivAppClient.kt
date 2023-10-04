package xyz.cssxsh.pixiv

import io.ktor.http.*
import kotlinx.coroutines.*
import xyz.cssxsh.pixiv.auth.*

/**
 * PixivClient，Multiplatform interface
 */
public interface PixivAppClient : UseHttpClient, UseConfig, CoroutineScope {

    public suspend fun login(block: suspend (Url) -> String): AuthResult

    public suspend fun refresh(): AuthResult

    public suspend fun info(): AuthResult

    public val refreshToken: String

    public val deviceToken: String

    public val ageLimit: AgeLimit
}
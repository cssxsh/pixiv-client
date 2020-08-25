package xyz.cssxsh.pixiv.client.exception

import kotlinx.serialization.json.Json
import xyz.cssxsh.pixiv.client.data.AuthError

class AuthException(
    json: String
) : Error() {
    val data: AuthError = Json.decodeFromString(AuthError.serializer(), json)

    override val message: String?
        get() = data.errors.toString()
}
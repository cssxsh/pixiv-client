package xyz.cssxsh.pixiv.client.exception

import kotlinx.serialization.json.Json
import xyz.cssxsh.pixiv.client.data.ApiError

class ApiException(
    json: String
) : Error() {
    val data: ApiError = Json.decodeFromString(ApiError.serializer(), json)

    override val message: String?
        get() = data.error.toString()
}
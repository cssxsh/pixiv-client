package xyz.cssxsh.pixiv.auth

import kotlinx.serialization.*

@Serializable
data class WebLoginResult(
    @SerialName("body")
    val body: Body,
    @SerialName("error")
    val error: Boolean,
    @SerialName("message")
    val message: String
) {
    @Serializable
    data class Body(
        @SerialName("validation_errors")
        val validationErrors: Map<String, String> = emptyMap()
    )
}
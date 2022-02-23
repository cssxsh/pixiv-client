package xyz.cssxsh.pixiv.auth

import kotlinx.serialization.*

@Serializable
public data class WebLoginResult(
    @SerialName("body")
    val body: Body,
    @SerialName("error")
    val error: Boolean,
    @SerialName("message")
    val message: String
) {
    @Serializable
    public data class Body(
        @SerialName("validation_errors")
        val validationErrors: Map<String, String> = emptyMap()
    )
}
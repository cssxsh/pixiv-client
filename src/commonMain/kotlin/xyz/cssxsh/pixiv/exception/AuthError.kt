package xyz.cssxsh.pixiv.exception

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class AuthError(
    @SerialName("has_error")
    val hasError: Boolean,
    @SerialName("error")
    val error: String,
    @SerialName("errors")
    val errors: Map<String, ErrorInfo>,
) {
    @Serializable
    public data class ErrorInfo(
        @SerialName("message")
        val message: String,
        @SerialName("code")
        val code: Int,
    )
}
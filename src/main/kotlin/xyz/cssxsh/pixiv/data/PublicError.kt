package xyz.cssxsh.pixiv.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublicError(
    @SerialName("errors")
    val errors: Map<String, Error>,
    @SerialName("has_error")
    val hasError: Boolean,
    @SerialName("status")
    val status: String
) {

    @Serializable
    data class Error(
        @SerialName("code")
        val code: Int,
        @SerialName("message")
        val message: String
    )
}
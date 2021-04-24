package xyz.cssxsh.pixiv.apps


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StampData(
    @SerialName("stamps")
    val stamps: List<Stamp>
) {
    @Serializable
    data class Stamp(
        @SerialName("stamp_id")
        val stampId: Int,
        @SerialName("stamp_url")
        val stampUrl: String
    )
}
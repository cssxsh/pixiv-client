package xyz.cssxsh.pixiv.apps

import kotlinx.serialization.*

@Serializable
public data class StampData(
    @SerialName("stamps")
    val stamps: List<Stamp>
) {
    @Serializable
    public data class Stamp(
        @SerialName("stamp_id")
        val stampId: Int,
        @SerialName("stamp_url")
        val stampUrl: String
    )
}
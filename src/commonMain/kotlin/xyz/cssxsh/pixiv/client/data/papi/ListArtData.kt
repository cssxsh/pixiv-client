package xyz.cssxsh.pixiv.client.data.papi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListArtData(
    @SerialName("status")
    val status: String,
    @SerialName("response")
    val works: List<ArtWork>,
    @SerialName("count")
    val count: Int,
    @SerialName("pagination")
    val pagination: Pagination
)
package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlyFilter(
    @SerialName("filter")
    val filter: String = "for_ios"
)
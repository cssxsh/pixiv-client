package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OnlyPID(
    @SerialName("illust_id")
    val pid: String
)
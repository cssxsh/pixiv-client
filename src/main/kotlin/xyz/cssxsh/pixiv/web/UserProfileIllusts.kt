package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
public data class UserProfileIllusts(
    @SerialName("works")
    @Serializable(WebApiMap.Illust::class)
    val works: Map<Long, WebIllust>
)
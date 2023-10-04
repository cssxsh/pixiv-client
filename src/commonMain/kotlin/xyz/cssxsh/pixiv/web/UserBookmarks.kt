package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
public data class UserBookmarks<T : WebWorkInfo>(
    @SerialName("total")
    val total: Int,
    @SerialName("works")
    val works: List<T>
)
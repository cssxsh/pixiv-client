package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.Serializable


interface AppRESTful<P, R> {
    val data: P
    val url: String
    val method: String
    val result: R?

    fun copyResult(result: R): AppRESTful<P, R>
}




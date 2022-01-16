package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.features.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.sync.*
import kotlinx.serialization.*
import xyz.cssxsh.pixiv.*
import java.util.*

abstract class FanBoxApi {
    companion object {
        internal val contexts = WeakHashMap<String, MetaData>()

        internal val mutex = Mutex()
    }

    abstract val client: PixivWebClient

    /**
     * @see [FanBoxUser.getMetaData]
     */
    protected suspend fun getMetaData(url: String): MetaData {
        val html = client.useHttpClient { http ->
            http.get<String>(url) {
                header(HttpHeaders.Origin, "https://www.fanbox.cc")
                header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
            }
        }
        val text = html.substringAfter("metadata")
            .substringAfter("content='").substringBeforeLast("'>")

        return PixivJson.decodeFromString(text)
    }

    protected open suspend fun getMetaData(): MetaData {
        val sessid = requireNotNull(
            client.storage
                .get(Url(urlString = "https://www.fanbox.cc/"))
                .get(name = "FANBOXSESSID")
        ) { "Not Found FANBOXSESSID" }
            .value

        return mutex.withLock {
            contexts.getOrPut(sessid) { getMetaData(url = "https://www.fanbox.cc/") }
        }
    }
}
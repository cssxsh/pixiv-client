package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import xyz.cssxsh.pixiv.PixivJson
import xyz.cssxsh.pixiv.PixivWebClient

public abstract class FanBoxApi {
    public companion object {
        internal val contexts = HashMap<String, MetaData>()

        internal val mutex = Mutex()
    }

    public abstract val client: PixivWebClient

    /**
     * @see [FanBoxUser.getMetaData]
     */
    protected suspend fun getMetaData(url: String): MetaData {
        val html: String = client.useHttpClient { client ->
            client.get(url) {
                header(HttpHeaders.Origin, "https://www.fanbox.cc")
                header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
            }.body()
        }
        val text = html.substringAfter("metadata")
            .substringAfter("content='").substringBeforeLast("'>")

        return PixivJson.decodeFromString(MetaData.serializer(), text)
    }

    protected open suspend fun getMetaData(): MetaData {
        val cookie = client.storage
            .get(Url(urlString = "https://www.fanbox.cc/"))
            .get(name = "FANBOXSESSID")
        val session = requireNotNull(cookie) { "Not Found FANBOXSESSID" }.value

        return mutex.withLock {
            contexts.getOrPut(session) { getMetaData(url = "https://www.fanbox.cc/") }
        }
    }
}
package xyz.cssxsh.pixiv

import io.ktor.http.*
import io.ktor.util.date.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okio.FileSystem
import okio.Path.Companion.toPath
import kotlin.test.Test

internal class OtherAuthKtTest {

    private val client = SimplePixivClient(config = PixivConfig())

    @Serializable
    data class EditThisCookie(
        @SerialName("domain")
        val domain: String,
        @SerialName("expirationDate")
        val expirationDate: Double? = null,
        @SerialName("hostOnly")
        val hostOnly: Boolean,
        @SerialName("httpOnly")
        val httpOnly: Boolean,
        @SerialName("id")
        val id: Int = 0,
        @SerialName("name")
        val name: String,
        @SerialName("path")
        val path: String,
        @SerialName("sameSite")
        val sameSite: String,
        @SerialName("secure")
        val secure: Boolean,
        @SerialName("session")
        val session: Boolean,
        @SerialName("storeId")
        val storeId: String,
        @SerialName("value")
        val value: String
    )

    fun EditThisCookie.toCookie() = Cookie(
        name = name,
        value = value,
        encoding = CookieEncoding.DQUOTES,
        expires = expirationDate?.run { GMTDate(times(1000).toLong()) },
        domain = domain,
        path = path,
        secure = secure,
        httpOnly = httpOnly
    )

    @Test
    @Suppress("BlockingMethodInNonBlockingContext")
    fun sina(): Unit = runBlocking {
        val result = client.sina { qrcode ->
            println(qrcode)
        }
        println(result)
    }

    @Test
    fun cookie(): Unit = runBlocking {
        val result = client.cookie {
            PixivJson.decodeFromString<List<EditThisCookie>>(
                FileSystem.SYSTEM.read(".run/cookie.json".toPath()) { readUtf8() }
            ).map { it.toCookie() }
        }
        println(result)
    }
}
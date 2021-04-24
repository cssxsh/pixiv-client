package xyz.cssxsh.pixiv.web

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jsoup.Jsoup
import xyz.cssxsh.pixiv.*
import java.util.*

private fun url(aid: Long, locale: Locale) = "https://www.pixivision.net/${locale.language}/a/${aid}"

@Serializable
data class PixivArticle(
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("illusts")
    val illusts: List<Illust>,
) {
    @Serializable
    data class Illust(
        @SerialName("pid")
        val pid: Long,
        @SerialName("title")
        val title: String,
        @SerialName("uid")
        val uid: Long,
        @SerialName("name")
        val name: String,
    )
}

private fun String.parsePixivArticle(): PixivArticle = Jsoup.parse(this).let { document ->
    PixivArticle(
        title = document.select(".am__title").text(),
        description = document.select(".am__description").text(),
        illusts = document.select(".am__work").map { element ->
            PixivArticle.Illust(
                pid = element.select(".am__work__title a")
                    .attr("href").substringAfterLast("/").toLong(),
                title = element.select(".am__work__title a")
                    .text(),
                uid = element.select(".am__work__user-name a")
                    .attr("href").substringAfterLast("/").toLong(),
                name = element.select(".am__work__user-name a")
                    .text()
            )
        }
    )
}

internal suspend fun UseHttpClient.getArticle(
    url: String,
    locale: Locale = Locale.CHINA
) = useHttpClient { client ->
    client.get<String>(url) {
        header(HttpHeaders.AcceptLanguage, locale.language)
        header(HttpHeaders.Referrer, url)
    }
}.parsePixivArticle()

internal suspend fun UseHttpClient.getArticle(
    aid: Long,
    locale: Locale = Locale.CHINA
) = getArticle(url = url(aid = aid, locale = locale), locale = locale)
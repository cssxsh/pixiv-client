package xyz.cssxsh.pixiv.apps

import io.ktor.client.call.*
import io.ktor.client.request.*
import xyz.cssxsh.pixiv.*

public suspend fun UseHttpClient.spotlightArticles(
    category: CategoryType? = null,
    filter: FilterType? = null,
    offset: Long? = null,
    url: String = SPOTLIGHT_ARTICLES,
): SpotlightArticleData = useHttpClient { client ->
    client.get(url) {
        parameter("category", category)
        parameter("offset", offset)
        parameter("filter", filter)
    }.body()
}
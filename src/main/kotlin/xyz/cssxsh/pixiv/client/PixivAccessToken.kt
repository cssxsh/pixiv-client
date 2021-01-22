package xyz.cssxsh.pixiv.client

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

class PixivAccessToken(val taken: suspend () -> String) {

    class Config(var taken: (suspend () -> String)? = null)

    companion object : HttpClientFeature<Config, PixivAccessToken> {

        val PixivAuthMark = AttributeKey<Unit>("PixivAuthMark")

        override val key: AttributeKey<PixivAccessToken> = AttributeKey("PixivAccessToken")

        override fun prepare(block: Config.() -> Unit) =
            PixivAccessToken(Config().apply(block).taken.let { checkNotNull(it) })

        override fun install(feature: PixivAccessToken, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.State) {
                if (context.attributes.contains(PixivAuthMark).not()) {
                    context.header(HttpHeaders.Authorization, "Bearer ${feature.taken()}")
                }
            }
        }
    }
}
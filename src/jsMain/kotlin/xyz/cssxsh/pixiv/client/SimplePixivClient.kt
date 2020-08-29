package xyz.cssxsh.pixiv.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer

actual open class SimplePixivClient
actual constructor(override val config: PixivConfig) : PixivClient, AbstractPixivClient() {
    actual constructor(block: PixivConfig.() -> Unit) : this(PixivConfig().apply(block))

    override var httpClient: HttpClient = HttpClient(Js) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }
}
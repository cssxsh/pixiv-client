package xyz.cssxsh.pixiv.web

import kotlinx.serialization.*

@Serializable
public data class UserProfileTop(
    @SerialName("illusts")
    @Serializable(WebApiMap.Illust::class)
    val illusts: Map<Long, WebIllust>,
    @SerialName("manga")
    @Serializable(WebApiMap.Illust::class)
    val manga: Map<Long, WebIllust>,
    @SerialName("novels")
    @Serializable(WebApiMap.Novel::class)
    val novels: Map<Long, WebNovel>,
    @SerialName("requestPostWorks")
    val works: RequestPostWorks,
) {

    @Serializable
    public data class RequestPostWorks(
        @SerialName("artworks")
        val artworks: List<WebIllust>,
        @SerialName("novels")
        val novels: List<WebNovel>
    )
}
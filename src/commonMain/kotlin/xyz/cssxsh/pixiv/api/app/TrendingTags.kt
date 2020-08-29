package xyz.cssxsh.pixiv.api.app

import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.data.app.TrendTagData

@Serializable
data class TrendingTags(
    override val data: OnlyFilter,
    override val url: String,
    override val method: String = "GET",
    override val result: TrendTagData? = null
): AppRESTful<OnlyFilter, TrendTagData> {

    override fun copyResult(result: TrendTagData): AppRESTful<OnlyFilter, TrendTagData> =
        copy(result = result)
}
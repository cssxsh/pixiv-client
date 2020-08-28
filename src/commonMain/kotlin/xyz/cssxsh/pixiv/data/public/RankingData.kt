package xyz.cssxsh.pixiv.data.public

import com.soywiz.klock.wrapped.WDate
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import xyz.cssxsh.pixiv.data.DateSerializer

@Serializable
data class RankingData(
    @SerialName("status")
    val status: String,
    @SerialName("response")
    val infos: List<RankingInfo>,
    @SerialName("count")
    val count: Int,
    @SerialName("pagination")
    val pagination: Pagination
) {
    @Serializable
    data class RankingInfo(
        @SerialName("content")
        val content: String,
        @SerialName("date")
        @Serializable(DateSerializer::class)
        val date: WDate,
        @SerialName("mode")
        val mode: String,
        @SerialName("works")
        val works: List<RankingWork>
    ) {
        @Serializable
        data class RankingWork(
            @SerialName("previous_rank")
            val previousRank: Int,
            @SerialName("rank")
            val rank: Int,
            @SerialName("work")
            val work: ArtWork
        )
    }
}
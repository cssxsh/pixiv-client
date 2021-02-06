package xyz.cssxsh.pixiv.data.publics

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import java.time.LocalDate
import xyz.cssxsh.pixiv.data.ISODateSerializer

@Serializable
data class RankingData(
    @SerialName("status")
    val status: String,
    @SerialName("response")
    val infos: List<RankingInfo>,
    @SerialName("count")
    val count: Int,
    @SerialName("pagination")
    val pagination: Pagination,
) {
    @Serializable
    data class RankingInfo(
        @SerialName("content")
        val content: String,
        @SerialName("date")
        @Serializable(with = ISODateSerializer::class)
        val date: LocalDate,
        @SerialName("mode")
        val mode: String,
        @SerialName("works")
        val works: List<RankingWork>,
    ) {
        @Serializable
        data class RankingWork(
            @SerialName("previous_rank")
            val previousRank: Int,
            @SerialName("rank")
            val rank: Int,
            @SerialName("work")
            val work: ArtWork,
        )
    }
}
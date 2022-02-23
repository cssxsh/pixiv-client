package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*
import java.time.*

@Serializable
public data class PaidRecord(
    @SerialName("creator")
    val creator: CreatorActive,
    @SerialName("id")
    val id: Long,
    @SerialName("paidAmount")
    val paidAmount: Int,
    @Contextual
    @SerialName("paymentDatetime")
    val paymentDatetime: OffsetDateTime,
    @SerialName("paymentMethod")
    val paymentMethod: String
)

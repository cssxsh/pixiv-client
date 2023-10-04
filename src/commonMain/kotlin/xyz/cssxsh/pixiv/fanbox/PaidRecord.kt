package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
public data class PaidRecord(
    @SerialName("creator")
    val creator: CreatorActive,
    @SerialName("id")
    val id: Long,
    @SerialName("paidAmount")
    val paidAmount: Int,

    @SerialName("paymentDatetime")
    val paymentDatetime: String,
    @SerialName("paymentMethod")
    val paymentMethod: String
)

package xyz.cssxsh.pixiv.data.app

import com.soywiz.klock.KlockLocale
import com.soywiz.klock.PatternDateFormat
import com.soywiz.klock.locale.japanese
import com.soywiz.klock.wrapped.WDateTimeTz
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import xyz.cssxsh.pixiv.data.DateTimeSerializer

@Serializable
data class CommentInfo(
    @SerialName("comment")
    val comment: String,
    @SerialName("date")
    @Serializable(with = CreateDateSerializer::class)
    val date: WDateTimeTz,
    @SerialName("has_replies")
    val hasReplies: Boolean,
    @SerialName("id")
    val id: Long,
    @SerialName("user")
    val user: UserInfo
) {
    companion object {
        object CreateDateSerializer : DateTimeSerializer(
            PatternDateFormat(
                format = "yyyy-MM-dd'T'HH:mm:ssXXX",
                KlockLocale.japanese
            )
        )
    }
}
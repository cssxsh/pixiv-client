package xyz.cssxsh.pixiv

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

@InternalSerializationApi
internal class PixivEnumSerializerTest {

    @Serializable
    data class Order(
        val type: OrderType,
    )

    @Test
    fun test() {
        // kotlinx.serialization.internal.EnumSerializer
        Json.encodeToString(Order.serializer(), Order(OrderType.ASC)).let {
            println(it)
        }
        Json.encodeToString(Order.serializer(), Order(OrderType.DESC)).let {
            println(it)
        }
        Json.decodeFromString(Order.serializer(), """{"type":"ASC"}""").let {
            println(it.toString())
        }
    }
}
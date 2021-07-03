package xyz.cssxsh.pixiv

import io.ktor.client.features.cookies.*
import io.ktor.http.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.properties.ReadOnlyProperty

private inline fun <reified T : Any, reified R> reflect() = ReadOnlyProperty<T, R> { thisRef, property ->
    thisRef::class.java.getDeclaredField(property.name).apply { isAccessible = true }.get(thisRef) as R
}

private val AcceptAllCookiesStorage.mutex: Mutex by reflect()

private val AcceptAllCookiesStorage.container: MutableList<Cookie> by reflect()

/**
 * 获取所有cookie值
 */
suspend fun AcceptAllCookiesStorage.all(): List<Cookie> = mutex.withLock { container }

/**
 * 保存载入cookie值
 */
suspend fun AcceptAllCookiesStorage.save(list: List<Cookie>) = mutex.withLock { container.addAll(list) }
@file:Suppress("unused")

package xyz.cssxsh.pixiv.tool

import com.soywiz.klock.TimeSpan
import com.soywiz.klock.minutes
import com.soywiz.klock.wrapped.WDateTimeTz
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import xyz.cssxsh.pixiv.ContentType
import xyz.cssxsh.pixiv.PublicityType
import xyz.cssxsh.pixiv.WorkType
import xyz.cssxsh.pixiv.api.app.illustFollow
import xyz.cssxsh.pixiv.api.app.illustMyPixiv
import xyz.cssxsh.pixiv.api.app.illustNew
import xyz.cssxsh.pixiv.api.app.userIllusts
import xyz.cssxsh.pixiv.client.PixivClient
import xyz.cssxsh.pixiv.data.app.IllustInfo

fun PixivClient.task(
    name: String,
    block: suspend PixivClient.() -> Unit
) = launch(CoroutineName("${this::class.qualifiedName}#task: $name")) {
    block()
}

inline fun <reified T> PixivClient.timerTask(
    name: String,
    duration: TimeSpan,
    origin: T,
    crossinline block: suspend PixivClient.(prev: T) -> T
) = launch(CoroutineName("${this::class.qualifiedName}#timerTask: $name")) {
    var prev: T = origin
    while (isActive) {
        delay(duration.millisecondsLong)
        prev = block(prev)
    }
}

/**
 * 只会检查前30个新作品
 */
fun PixivClient.addUserListener(
    uid: Long,
    type: WorkType,
    start: WDateTimeTz = WDateTimeTz.nowLocal(),
    timerDuration: TimeSpan = (10).minutes,
    block: suspend PixivClient.(IllustInfo) -> Unit
) = timerTask(name = "UserListener($uid)", duration = timerDuration, origin = start) {
    userIllusts(uid = uid, type = type).illusts.filter {
             it.createDate > start
    }.apply {
        forEach { launch { block(it) }  }
    }.lastOrNull()?.createDate ?: start
}

/**
 * 只会检查前30个新作品
 */
fun PixivClient.addIllustNewListener(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    start: WDateTimeTz = WDateTimeTz.nowLocal(),
    timerDuration: TimeSpan = (10).minutes,
    block: suspend PixivClient.(IllustInfo) -> Unit
) = timerTask(name = "IllustNewListener(${getAuthInfoOrThrow().user.uid})", duration = timerDuration, origin = start) {
    illustNew(contentType = contentType, restrict = restrict).illusts.filter {
        it.createDate > start
    }.apply {
        forEach { launch { block(it) }  }
    }.lastOrNull()?.createDate ?: start
}

/**
 * 只会检查前30个新作品
 */
fun PixivClient.addIllustMyPixivListener(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    start: WDateTimeTz = WDateTimeTz.nowLocal(),
    timerDuration: TimeSpan = (10).minutes,
    block: suspend PixivClient.(IllustInfo) -> Unit
) = timerTask(name = "IllustMyPixivListener(${getAuthInfoOrThrow().user.uid})", duration = timerDuration, origin = start) {
    illustMyPixiv(contentType = contentType, restrict = restrict).illusts.filter {
        it.createDate > start
    }.apply {
        forEach { launch { block(it) }  }
    }.lastOrNull()?.createDate ?: start
}

/**
 * 只会检查前30个新作品
 */
fun PixivClient.addIllustFollowListener(
    contentType: ContentType = ContentType.ILLUST,
    restrict: PublicityType = PublicityType.PUBLIC,
    start: WDateTimeTz = WDateTimeTz.nowLocal(),
    timerDuration: TimeSpan = (10).minutes,
    block: suspend PixivClient.(IllustInfo) -> Unit
) = timerTask(name = "IllustFollowListener(${getAuthInfoOrThrow().user.uid})", duration = timerDuration, origin = start) {
    illustFollow(contentType = contentType, restrict = restrict).illusts.filter {
        it.createDate > start
    }.apply {
        forEach { launch { block(it) }  }
    }.lastOrNull()?.createDate ?: start
}
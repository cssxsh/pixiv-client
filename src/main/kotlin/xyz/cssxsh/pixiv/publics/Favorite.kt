package xyz.cssxsh.pixiv.publics

import io.ktor.client.request.*
import io.ktor.http.*
import xyz.cssxsh.pixiv.*

suspend fun PixivClient.addFavoriteWorks(
    pid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    url: String = ME_FAVORITE_WORKS,
): FavoriteWorkData = useHttpClient { client ->
    client.post(url) {
        header(HttpHeaders.Referrer, REFERER)

        parameter("work_id", pid)
        parameter("publicity", publicity.value())
    }
}

suspend fun PixivClient.deleteFavoriteWorks(
    pids: List<Long>,
    url: String = ME_FAVORITE_WORKS,
): FavoriteWorkData = useHttpClient { client ->
    client.delete(url) {
        header(HttpHeaders.Referrer, REFERER)

        parameter("ids", pids.joinToString(","))
    }
}
suspend fun PixivClient.addFavoriteUsers(
    uid: Long,
    publicity: PublicityType = PublicityType.PUBLIC,
    url: String = ME_FAVORITE_USERS,
): FavoriteUserData = useHttpClient { client ->
    client.post(url) {
        header(HttpHeaders.Referrer, REFERER)

        parameter("target_user_id", uid)
        parameter("publicity", publicity.value())
    }
}

suspend fun PixivClient.deleteFavoriteUsers(
    uids: List<Long>,
    url: String = ME_FAVORITE_USERS,
): FavoriteUserData = useHttpClient { client ->
    client.delete(url) {
        header(HttpHeaders.Referrer, REFERER)

        parameter("delete_ids", uids.joinToString(","))
    }
}
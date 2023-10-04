package xyz.cssxsh.pixiv.fanbox

import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.utils.io.core.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.web.*

public class FanBoxUser(override val client: PixivWebClient) : FanBoxApi() {
    public companion object {
        internal const val COUNT_UNREAD_MESSAGES = "https://api.fanbox.cc/user.countUnreadMessages"

        internal const val GET_TWITTER_ACCOUNT_INFO = "https://api.fanbox.cc/user.getTwitterAccountInfo"

        internal const val UPDATE = "https://api.fanbox.cc/user.update"
    }

    public suspend fun countUnreadMessages(): Int {
        return client.ajax(api = COUNT_UNREAD_MESSAGES) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    public suspend fun getTwitterAccountInfo(): TwitterAccountInfo {
        return client.ajax(api = GET_TWITTER_ACCOUNT_INFO) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
        }
    }

    public override suspend fun getMetaData(): MetaData = super.getMetaData(url = "https://www.fanbox.cc/user/settings")

    /**
     * TODO: set twitter by https://www.fanbox.cc/twitter_oauth?source=account
     */
    public suspend fun update(setting: UserUpdate = UserUpdate(), icon: ByteArray? = null): UserSetting {
        val metadata = getMetaData()
        val name = setting.name ?: metadata.context.user.name
        val showAdultContent = setting.showAdultContent ?: metadata.context.user.showAdultContent
        val socialConnectTwitter = setting.socialConnectTwitter ?: metadata.context.user.socialConnectStatus.twitter

        return client.ajax(api = UPDATE) {
            header(HttpHeaders.Origin, "https://www.fanbox.cc")
            header(HttpHeaders.Referrer, "https://www.fanbox.cc/")
            header(HttpHeaders.XCsrfToken, metadata.csrfToken)

            method = HttpMethod.Post

            setBody(MultiPartFormDataContent(parts = formData {
                append(key = "name", value = name)
                append(key = "showAdultContent", value = if (showAdultContent) 1 else 0)
                append(key = "socialConnectTwitter", value = if (socialConnectTwitter) 1 else 0)
                if (icon != null) append(key = "icon", filename = "blob") { writeFully(src = icon) }
                append(key = "tt", value = metadata.csrfToken)
            }))
        }
    }
}
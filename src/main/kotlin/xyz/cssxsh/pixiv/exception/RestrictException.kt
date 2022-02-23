package xyz.cssxsh.pixiv.exception

import xyz.cssxsh.pixiv.apps.*

public class RestrictException(public val illust: IllustInfo) : IllegalArgumentException() {
    override val message: String = "[${illust.pid}] 作品已删除或者被限制, Redirect: ${illust.getOriginImageUrls()}"
}
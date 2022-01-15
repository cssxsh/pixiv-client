package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.*
import java.time.*

@Serializable
sealed class PostDetail(val type: String) {
    abstract val body: Body?
    abstract val commentCount: Int
    abstract val commentList: CommentList
    abstract val coverImageUrl: String?
    abstract val creatorId: String
    abstract val excerpt: String
    abstract val feeRequired: Int
    abstract val hasAdultContent: Boolean
    abstract val id: Long
    abstract val imageForShare: String?
    abstract val isLiked: Boolean
    abstract val isRestricted: Boolean
    abstract val likeCount: Int
    abstract val nextPost: Link?
    abstract val prevPost: Link?
    abstract val publishedDatetime: OffsetDateTime
    abstract val restrictedFor: Int?
    abstract val tags: List<String>
    abstract val title: String
    abstract val updatedDatetime: OffsetDateTime
    abstract val user: CreatorInfo

    @Serializable
    @SerialName("article")
    data class Article(
        @SerialName("body")
        override val body: ArticleBody?,
        @SerialName("commentCount")
        override val commentCount: Int,
        @SerialName("commentList")
        override val commentList: CommentList = CommentList.Empty,
        @SerialName("coverImageUrl")
        override val coverImageUrl: String?,
        @SerialName("creatorId")
        override val creatorId: String,
        @SerialName("excerpt")
        override val excerpt: String,
        @SerialName("feeRequired")
        override val feeRequired: Int,
        @SerialName("hasAdultContent")
        override val hasAdultContent: Boolean,
        @SerialName("id")
        override val id: Long,
        @SerialName("imageForShare")
        override val imageForShare: String? = null,
        @SerialName("isLiked")
        override val isLiked: Boolean,
        @SerialName("isRestricted")
        override val isRestricted: Boolean,
        @SerialName("likeCount")
        override val likeCount: Int,
        @SerialName("nextPost")
        override val nextPost: Link? = null,
        @SerialName("prevPost")
        override val prevPost: Link? = null,
        @Contextual
        @SerialName("publishedDatetime")
        override val publishedDatetime: OffsetDateTime,
        @SerialName("restrictedFor")
        override val restrictedFor: Int?,
        @SerialName("tags")
        override val tags: List<String>,
        @SerialName("title")
        override val title: String,
        @Contextual
        @SerialName("updatedDatetime")
        override val updatedDatetime: OffsetDateTime,
        @SerialName("user")
        override val user: CreatorInfo
    ) : PostDetail(type = "article")

    @Serializable
    @SerialName("file")
    data class File(
        @SerialName("body")
        override val body: FileBody?,
        @SerialName("commentCount")
        override val commentCount: Int,
        @SerialName("commentList")
        override val commentList: CommentList = CommentList.Empty,
        @SerialName("coverImageUrl")
        override val coverImageUrl: String,
        @SerialName("creatorId")
        override val creatorId: String,
        @SerialName("excerpt")
        override val excerpt: String,
        @SerialName("feeRequired")
        override val feeRequired: Int,
        @SerialName("hasAdultContent")
        override val hasAdultContent: Boolean,
        @SerialName("id")
        override val id: Long,
        @SerialName("imageForShare")
        override val imageForShare: String? = null,
        @SerialName("isLiked")
        override val isLiked: Boolean,
        @SerialName("isRestricted")
        override val isRestricted: Boolean,
        @SerialName("likeCount")
        override val likeCount: Int,
        @SerialName("nextPost")
        override val nextPost: Link? = null,
        @SerialName("prevPost")
        override val prevPost: Link? = null,
        @Contextual
        @SerialName("publishedDatetime")
        override val publishedDatetime: OffsetDateTime,
        @SerialName("restrictedFor")
        override val restrictedFor: Int?,
        @SerialName("tags")
        override val tags: List<String>,
        @SerialName("title")
        override val title: String,
        @Contextual
        @SerialName("updatedDatetime")
        override val updatedDatetime: OffsetDateTime,
        @SerialName("user")
        override val user: CreatorInfo
    ) : PostDetail(type = "file")

    @Serializable
    @SerialName("image")
    data class Image(
        @SerialName("body")
        override val body: ImageBody?,
        @SerialName("commentCount")
        override val commentCount: Int,
        @SerialName("commentList")
        override val commentList: CommentList = CommentList.Empty,
        @SerialName("coverImageUrl")
        override val coverImageUrl: String?,
        @SerialName("creatorId")
        override val creatorId: String,
        @SerialName("excerpt")
        override val excerpt: String,
        @SerialName("feeRequired")
        override val feeRequired: Int,
        @SerialName("hasAdultContent")
        override val hasAdultContent: Boolean,
        @SerialName("id")
        override val id: Long,
        @SerialName("imageForShare")
        override val imageForShare: String? = null,
        @SerialName("isLiked")
        override val isLiked: Boolean,
        @SerialName("isRestricted")
        override val isRestricted: Boolean,
        @SerialName("likeCount")
        override val likeCount: Int,
        @SerialName("nextPost")
        override val nextPost: Link? = null,
        @SerialName("prevPost")
        override val prevPost: Link? = null,
        @Contextual
        @SerialName("publishedDatetime")
        override val publishedDatetime: OffsetDateTime,
        @SerialName("restrictedFor")
        override val restrictedFor: Int?,
        @SerialName("tags")
        override val tags: List<String>,
        @SerialName("title")
        override val title: String,
        @Contextual
        @SerialName("updatedDatetime")
        override val updatedDatetime: OffsetDateTime,
        @SerialName("user")
        override val user: CreatorInfo
    ) : PostDetail(type = "image")

    @Serializable
    @SerialName("text")
    data class Text(
        @SerialName("body")
        override val body: TextBody?,
        @SerialName("commentCount")
        override val commentCount: Int,
        @SerialName("commentList")
        override val commentList: CommentList = CommentList.Empty,
        @SerialName("coverImageUrl")
        override val coverImageUrl: String?,
        @SerialName("creatorId")
        override val creatorId: String,
        @SerialName("excerpt")
        override val excerpt: String,
        @SerialName("feeRequired")
        override val feeRequired: Int,
        @SerialName("hasAdultContent")
        override val hasAdultContent: Boolean,
        @SerialName("id")
        override val id: Long,
        @SerialName("imageForShare")
        override val imageForShare: String? = null,
        @SerialName("isLiked")
        override val isLiked: Boolean,
        @SerialName("isRestricted")
        override val isRestricted: Boolean,
        @SerialName("likeCount")
        override val likeCount: Int,
        @SerialName("nextPost")
        override val nextPost: Link? = null,
        @SerialName("prevPost")
        override val prevPost: Link? = null,
        @Contextual
        @SerialName("publishedDatetime")
        override val publishedDatetime: OffsetDateTime,
        @SerialName("restrictedFor")
        override val restrictedFor: Int?,
        @SerialName("tags")
        override val tags: List<String>,
        @SerialName("title")
        override val title: String,
        @Contextual
        @SerialName("updatedDatetime")
        override val updatedDatetime: OffsetDateTime,
        @SerialName("user")
        override val user: CreatorInfo
    ) : PostDetail(type = "text")

    sealed interface Body

    @Serializable
    data class ArticleBody(
        @SerialName("blocks")
        val blocks: List<ArticleBlock>,
        @SerialName("embedMap")
        val embedMap: Map<String, EmbedItem>,
        @SerialName("fileMap")
        val fileMap: Map<String, FileItem>,
        @SerialName("imageMap")
        val imageMap: Map<String, ImageItem>,
        @SerialName("urlEmbedMap")
        val urlEmbedMap: Map<String, UrlEmbedItem>,
    ) : Body

    @Serializable
    data class FileBody(
        @SerialName("files")
        val files: List<FileItem>,
        @SerialName("text")
        val text: String
    ) : Body

    @Serializable
    data class ImageBody(
        @SerialName("images")
        val images: List<ImageItem>,
        @SerialName("text")
        val text: String
    ) : Body

    @Serializable
    data class TextBody(
        @SerialName("text")
        val text: String
    ) : Body

    @Serializable
    sealed class ArticleBlock(val type: String) {

        sealed interface RichText {
            val offset: Int
            val length: Int
        }

        @Serializable
        sealed class Style(val type: String) : RichText {

            @Serializable
            @SerialName("bold")
            data class Bold(
                @SerialName("offset")
                override val offset: Int,
                @SerialName("length")
                override val length: Int,
            ) : Style(type = "bold")
        }

        @Serializable
        data class Link(
            @SerialName("offset")
            override val offset: Int,
            @SerialName("length")
            override val length: Int,
            @SerialName("url")
            val url: String
        ) : RichText

        @Serializable
        @SerialName("p")
        data class Paragraph(
            @SerialName("text")
            val text: String,
            @SerialName("styles")
            val styles: List<Style> = emptyList(),
            @SerialName("links")
            val links: List<Link> = emptyList()
        ) : ArticleBlock(type = "p")

        @Serializable
        @SerialName("header")
        data class Header(
            @SerialName("text")
            val text: String
        ) : ArticleBlock(type = "header")

        @Serializable
        @SerialName("embed")
        data class Embed(
            @SerialName("embedId")
            val embedId: String
        ) : ArticleBlock(type = "embed")

        @Serializable
        @SerialName("url_embed")
        data class UrlEmbed(
            @SerialName("urlEmbedId")
            val urlEmbedId: String
        ) : ArticleBlock(type = "url_embed")

        @Serializable
        @SerialName("image")
        data class Image(
            @SerialName("imageId")
            val imageId: String
        ) : ArticleBlock(type = "image")

        @Serializable
        @SerialName("file")
        data class File(
            @SerialName("fileId")
            val fileId: String
        ) : ArticleBlock(type = "file")
    }

    sealed interface Item {
        val id: String
    }

    @Serializable
    data class FileItem(
        @SerialName("extension")
        val extension: String,
        @SerialName("id")
        override val id: String,
        @SerialName("name")
        val name: String,
        @SerialName("size")
        val size: Long,
        @SerialName("url")
        val url: String,
    ) : Item

    @Serializable
    data class ImageItem(
        @SerialName("extension")
        val extension: String,
        @SerialName("height")
        val height: Int,
        @SerialName("id")
        override val id: String,
        @SerialName("originalUrl")
        val originalUrl: String,
        @SerialName("thumbnailUrl")
        val thumbnailUrl: String,
        @SerialName("width")
        val width: Int
    ) : Item

    @Serializable
    data class EmbedItem(
        @SerialName("id")
        override val id: String,
        @SerialName("serviceProvider")
        val serviceProvider: String,
        @SerialName("contentId")
        val contentId: String
    ) : Item

    @Serializable
    sealed class UrlEmbedItem(val type: String) : Item {

        @Serializable
        @SerialName("fanbox.creator")
        data class Profile(
            @SerialName("profile")
            val profile: CreatorDetail,
            @SerialName("id")
            override val id: String
        ) : UrlEmbedItem(type = "fanbox.creator")

        @Serializable
        @SerialName("fanbox.post")
        data class Post(
            @SerialName("postInfo")
            val postInfo: PostInfo,
            @SerialName("id")
            override val id: String
        ) : UrlEmbedItem(type = "fanbox.post")

        @Serializable
        @SerialName("html")
        data class Html(
            @SerialName("html")
            val html: String,
            @SerialName("id")
            override val id: String
        ) : UrlEmbedItem(type = "html")

        @Serializable
        @SerialName("html.card")
        data class Card(
            @SerialName("html")
            val card: String,
            @SerialName("id")
            override val id: String
        ) : UrlEmbedItem(type = "html.card")
    }

    @Serializable
    data class Link(
        @SerialName("id")
        val id: String,
        @SerialName("publishedDatetime")
        val publishedDatetime: String,
        @SerialName("title")
        val title: String
    )
}
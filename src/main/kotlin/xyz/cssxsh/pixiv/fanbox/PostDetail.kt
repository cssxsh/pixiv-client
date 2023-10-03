package xyz.cssxsh.pixiv.fanbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
public sealed class PostDetail {
    public abstract val body: Body?
    public abstract val commentCount: Int
    public abstract val commentList: CommentList
    public abstract val coverImageUrl: String?
    public abstract val creatorId: String
    public abstract val excerpt: String
    public abstract val feeRequired: Int
    public abstract val hasAdultContent: Boolean
    public abstract val id: Long
    public abstract val imageForShare: String?
    public abstract val isLiked: Boolean
    public abstract val isRestricted: Boolean
    public abstract val likeCount: Int
    public abstract val nextPost: Link?
    public abstract val prevPost: Link?
    public abstract val publishedDatetime: String
    public abstract val restrictedFor: Int?
    public abstract val tags: List<String>
    public abstract val title: String
    public abstract val updatedDatetime: String
    public abstract val user: CreatorInfo

    @Serializable
    @SerialName("article")
    public data class Article(
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

        @SerialName("publishedDatetime")
        override val publishedDatetime: String,
        @SerialName("restrictedFor")
        override val restrictedFor: Int?,
        @SerialName("tags")
        override val tags: List<String>,
        @SerialName("title")
        override val title: String,

        @SerialName("updatedDatetime")
        override val updatedDatetime: String,
        @SerialName("user")
        override val user: CreatorInfo
    ) : PostDetail()

    @Serializable
    @SerialName("file")
    public data class File(
        @SerialName("body")
        override val body: FileBody?,
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

        @SerialName("publishedDatetime")
        override val publishedDatetime: String,
        @SerialName("restrictedFor")
        override val restrictedFor: Int?,
        @SerialName("tags")
        override val tags: List<String>,
        @SerialName("title")
        override val title: String,

        @SerialName("updatedDatetime")
        override val updatedDatetime: String,
        @SerialName("user")
        override val user: CreatorInfo
    ) : PostDetail()

    @Serializable
    @SerialName("image")
    public data class Image(
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

        @SerialName("publishedDatetime")
        override val publishedDatetime: String,
        @SerialName("restrictedFor")
        override val restrictedFor: Int?,
        @SerialName("tags")
        override val tags: List<String>,
        @SerialName("title")
        override val title: String,

        @SerialName("updatedDatetime")
        override val updatedDatetime: String,
        @SerialName("user")
        override val user: CreatorInfo
    ) : PostDetail()

    @Serializable
    @SerialName("text")
    public data class Text(
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

        @SerialName("publishedDatetime")
        override val publishedDatetime: String,
        @SerialName("restrictedFor")
        override val restrictedFor: Int?,
        @SerialName("tags")
        override val tags: List<String>,
        @SerialName("title")
        override val title: String,

        @SerialName("updatedDatetime")
        override val updatedDatetime: String,
        @SerialName("user")
        override val user: CreatorInfo
    ) : PostDetail()

    @Serializable
    @SerialName("video")
    public data class Video(
        @SerialName("body")
        override val body: VideoBody?,
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

        @SerialName("publishedDatetime")
        override val publishedDatetime: String,
        @SerialName("restrictedFor")
        override val restrictedFor: Int?,
        @SerialName("tags")
        override val tags: List<String>,
        @SerialName("title")
        override val title: String,

        @SerialName("updatedDatetime")
        override val updatedDatetime: String,
        @SerialName("user")
        override val user: CreatorInfo
    ) : PostDetail()

    public sealed interface Body

    @Serializable
    public data class ArticleBody(
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
    public data class FileBody(
        @SerialName("files")
        val files: List<FileItem>,
        @SerialName("text")
        val text: String
    ) : Body

    @Serializable
    public data class ImageBody(
        @SerialName("images")
        val images: List<ImageItem>,
        @SerialName("text")
        val text: String
    ) : Body

    @Serializable
    public data class TextBody(
        @SerialName("text")
        val text: String
    ) : Body

    @Serializable
    public data class VideoBody(
        @SerialName("videos")
        val videos: List<EmbedItem>,
        @SerialName("text")
        val text: String
    ) : Body

    @Serializable
    public sealed class ArticleBlock {

        public sealed interface RichText {
            public val offset: Int
            public val length: Int
        }

        @Serializable
        public sealed class Style : RichText {

            @Serializable
            @SerialName("bold")
            public data class Bold(
                @SerialName("offset")
                override val offset: Int,
                @SerialName("length")
                override val length: Int,
            ) : Style()
        }

        @Serializable
        public data class Link(
            @SerialName("offset")
            override val offset: Int,
            @SerialName("length")
            override val length: Int,
            @SerialName("url")
            val url: String
        ) : RichText

        @Serializable
        @SerialName("p")
        public data class Paragraph(
            @SerialName("text")
            val text: String,
            @SerialName("styles")
            val styles: List<Style> = emptyList(),
            @SerialName("links")
            val links: List<Link> = emptyList()
        ) : ArticleBlock()

        @Serializable
        @SerialName("header")
        public data class Header(
            @SerialName("text")
            val text: String
        ) : ArticleBlock()

        @Serializable
        @SerialName("embed")
        public data class Embed(
            @SerialName("embedId")
            val embedId: String
        ) : ArticleBlock()

        @Serializable
        @SerialName("url_embed")
        public data class UrlEmbed(
            @SerialName("urlEmbedId")
            val urlEmbedId: String
        ) : ArticleBlock()

        @Serializable
        @SerialName("image")
        public data class Image(
            @SerialName("imageId")
            val imageId: String
        ) : ArticleBlock()

        @Serializable
        @SerialName("file")
        public data class File(
            @SerialName("fileId")
            val fileId: String
        ) : ArticleBlock()
    }

    public sealed interface Item {
        public val id: String
    }

    @Serializable
    public data class FileItem(
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
    public data class ImageItem(
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
    public data class EmbedItem(
        @SerialName("id")
        override val id: String,
        @SerialName("serviceProvider")
        val serviceProvider: String,
        @SerialName("contentId")
        val contentId: String
    ) : Item

    @Serializable
    public sealed class UrlEmbedItem : Item {

        @Serializable
        @SerialName("fanbox.creator")
        public data class Profile(
            @SerialName("profile")
            val profile: CreatorDetail,
            @SerialName("id")
            override val id: String
        ) : UrlEmbedItem()

        @Serializable
        @SerialName("fanbox.post")
        public data class Post(
            @SerialName("postInfo")
            val postInfo: PostInfo,
            @SerialName("id")
            override val id: String
        ) : UrlEmbedItem()

        @Serializable
        @SerialName("html")
        public data class Html(
            @SerialName("html")
            val html: String,
            @SerialName("id")
            override val id: String
        ) : UrlEmbedItem()

        @Serializable
        @SerialName("html.card")
        public data class Card(
            @SerialName("html")
            val card: String,
            @SerialName("id")
            override val id: String
        ) : UrlEmbedItem()


        @Serializable
        @SerialName("default")
        public data class Default(
            @SerialName("url")
            val url: String,
            @SerialName("host")
            val host: String,
            @SerialName("id")
            override val id: String
        ) : UrlEmbedItem()
    }

    @Serializable
    public data class Link(
        @SerialName("id")
        val id: String,
        @SerialName("publishedDatetime")
        val publishedDatetime: String,
        @SerialName("title")
        val title: String
    )
}
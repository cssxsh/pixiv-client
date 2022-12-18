package xyz.cssxsh.pixiv.tool

import com.squareup.gifencoder.*
import io.ktor.http.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.apps.*
import java.io.*
import kotlin.system.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PixivUgoiraEncoderTest : SummaryTest() {

    private val exampleDir = File("example")

    private val workDir = File("run")

    private val downloader: PixivDownloader = PixivDownloader()

    private val mediancut = object : PixivGifEncoder(downloader) {
        override val cache: File get() = workDir
        override val quantizer: ColorQuantizer = MedianCutQuantizer.INSTANCE
        override val ditherer: Ditherer = FloydSteinbergDitherer.INSTANCE
        override val disposalMethod: DisposalMethod = DisposalMethod.UNSPECIFIED
    }

    private val octtree = object : PixivGifEncoder(downloader) {
        override val cache: File get() = workDir
        override val quantizer: ColorQuantizer = OctTreeQuantizer.INSTANCE
        override val ditherer: Ditherer = FloydSteinbergDitherer.INSTANCE
        override val disposalMethod: DisposalMethod = DisposalMethod.UNSPECIFIED
    }

    private val opencv = object : PixivGifEncoder(downloader) {
        override val cache: File get() = workDir
        override val quantizer: ColorQuantizer = OpenCVQuantizer.INSTANCE
        override val ditherer: Ditherer = FloydSteinbergDitherer.INSTANCE
        override val disposalMethod: DisposalMethod = DisposalMethod.UNSPECIFIED
    }

    private val illust = PixivJson.decodeFromString<IllustInfo>(exampleDir.resolve("99565513.json").readText())

    private val ugoira = PixivJson.decodeFromString<UgoiraMetadata>(exampleDir.resolve("99565513.ugoira.json").readText())

    @BeforeAll
    override fun init() {
        super.init()

        val original = Url(ugoira.zipUrls.values.first().replace("600x600", "1920x1080"))
        workDir.resolve(original.encodedPath.substringAfterLast('/')).apply {
            if (exists().not()) {
                parentFile.mkdirs()
                runBlocking {
                    writeBytes(downloader.download(original))
                }
            }
        }

        println("# Ugoira Encoder Test")
        println("| QUANTIZER | SIZE | DURATION |")
        println("|:---------:|:----:|:--------:|")
    }

    @Test
    fun mediancut(): Unit = runBlocking {
        val file: File
        val millis = measureTimeMillis {
            file = mediancut.encode(illust = illust, metadata = ugoira)
        }
        assertTrue(file.exists())
        println("| MedianCut | ${file.length() shr 10}KB | ${java.time.Duration.ofMillis(millis)} |")
    }

    @Test
    fun octtree(): Unit = runBlocking {
        val file: File
        val millis = measureTimeMillis {
            file = octtree.encode(illust = illust, metadata = ugoira)
        }
        assertTrue(file.exists())
        println("| OctTree | ${file.length() shr 10}KB | ${java.time.Duration.ofMillis(millis)} |")
    }

    @Test
    fun opencv(): Unit = runBlocking {
        val file: File
        val millis = measureTimeMillis {
            file = opencv.encode(illust = illust, metadata = ugoira)
        }

        assertTrue(file.exists())
        println("| OpenCV | ${file.length() shr 10}KB | ${java.time.Duration.ofMillis(millis)} |")
    }
}
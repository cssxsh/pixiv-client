package xyz.cssxsh.pixiv.apps

import com.squareup.gifencoder.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import lunartools.apng.ApngBuilder
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.tool.*
import java.io.*
import kotlin.time.*

@OptIn(ExperimentalTime::class)
internal class UgoiraKtTest : ApiTest() {

    @Test
    fun ugoiraMetadata(): Unit = runBlocking {
        with(client.ugoiraMetadata(79007274L).ugoira) {
            assertTrue(frames.isNotEmpty())
            assertTrue(zipUrls.isNotEmpty())

            println(frames)
            println(zipUrls)
        }
    }

    private val work = File("../test")

    private val gif: PixivUgoiraEncoder = object : PixivGifEncoder() {
        override val dir: File get() = work
        override val quantizer: ColorQuantizer = MedianCutQuantizer.INSTANCE
        override val ditherer: Ditherer = FloydSteinbergDitherer.INSTANCE
        override val disposalMethod: DisposalMethod = DisposalMethod.UNSPECIFIED
    }

    private val png: PixivUgoiraEncoder = object : PixivPngEncoder() {
        override val dir: File get() = work
        override val quantizer: ApngBuilder.QuantizerAlgorithm = ApngBuilder.QuantizerAlgorithm.MEDIAN_CUT
        override val dithering: ApngBuilder.DitheringAlgorithm = ApngBuilder.DitheringAlgorithm.SIERRA
        override val encoder = true
        override val reencode = true
    }

    @Test
    fun gif(): Unit = runBlocking {
        val illust = Json.decodeFromString<IllustInfo>(work.resolve("87036967.json").readText())
        val ugoira = Json.decodeFromString<UgoiraMetadata>(work.resolve("87036967.ugoira.json").readText())
        val (file, duration) = measureTimedValue {
            gif.encode(
                pid = illust.pid,
                metadata = ugoira,
                width = illust.width,
                height = illust.height
            )
        }
        assertTrue(file.exists())
        println("gif")
        println("${file.length() shr 20}MB")
        println(duration)
    }

    @Test
    fun png(): Unit = runBlocking {
        val illust = Json.decodeFromString<IllustInfo>(work.resolve("87036967.json").readText())
        val ugoira = Json.decodeFromString<UgoiraMetadata>(work.resolve("87036967.ugoira.json").readText())
        val (file, duration) = measureTimedValue {
            png.encode(
                pid = illust.pid,
                metadata = ugoira,
                width = illust.width,
                height = illust.height
            )
        }
        assertTrue(file.exists())
        println("png")
        println("${file.length() shr 20}MB")
        println(duration)
    }
}
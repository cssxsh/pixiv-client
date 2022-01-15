package xyz.cssxsh.pixiv.tool

//import org.bytedeco.javacv.*
//import org.bytedeco.opencv.opencv_core.*
//import org.bytedeco.opencv.*
//import org.bytedeco.opencv.global.opencv_core.*
import com.squareup.gifencoder.*
import kotlinx.coroutines.*
import kotlinx.serialization.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import xyz.cssxsh.pixiv.*
import xyz.cssxsh.pixiv.apps.*
import java.io.*
import kotlin.time.*

@OptIn(ExperimentalTime::class)
internal class PixivUgoiraEncoderTest {
    init {
//        System.setProperty("javacpp.platform", "windows-x86_64-gpu")
//        opencv_java()
//        setUseOpenCL(true)
//        setDevice(0)
    }

//    @Test
//    fun info() {
//        println(haveOpenCL())
//        println(getCudaEnabledDeviceCount())
//        println(useOpenCL())
//        finish()
//
//        println(PlatformInfo().apply {
//            getPlatfomsInfo(this)
//        }.empty())
//    }

    private val work = File("../test")

    private val red = Color::class.java.getDeclaredField("red").apply { isAccessible = true }

    private val green = Color::class.java.getDeclaredField("green").apply { isAccessible = true }

    private val blue = Color::class.java.getDeclaredField("blue").apply { isAccessible = true }

    private var offset = 0

//    private val javacv = ColorQuantizer { originalColors, maxColorCount ->
//        println(offset++)
//
//        val original = Mat(originalColors.size, 1, CV_64FC3).apply {
//            val buffer = createBuffer<DoubleBuffer>()
//            for (color in originalColors) {
//                buffer.put(red.get(color) as Double)
//                buffer.put(green.get(color) as Double)
//                buffer.put(blue.get(color) as Double)
//            }
//        }
//        val original32F = Mat()
//        val src = GpuMat().apply {
//            original.convertTo(original32F, CV_32F)
//            upload(original32F)
//        }
//
//        val labels = GpuMat()
//        val criteria = TermCriteria()
//        // TermCriteria(TermCriteria.EPS, 3, 1.0)
//        val centers = GpuMat()
//
//        kmeans(
//            src,
//            maxColorCount,
//            labels,
//            criteria,
//            1,
//            KMEANS_PP_CENTERS,
//            centers
//        )
//
//        val colors = mutableSetOf<Color>()
//        val temp = Mat().apply { centers.convertTo(this, CV_64F) }
//        val buffer = temp.createBuffer<DoubleBuffer>()
//        for (x in 0 until centers.rows()) {
//            val r = buffer[x * 3 + 0]
//            val g = buffer[x * 3 + 1]
//            val b = buffer[x * 3 + 2]
//            colors.add(Color(r, g, b))
//        }
//
//        colors
//    }

    private val gif: PixivUgoiraEncoder = object : PixivGifEncoder() {
        override val cache: File get() = work
        override val quantizer: ColorQuantizer = OctTreeQuantizer.INSTANCE
        override val ditherer: Ditherer = FloydSteinbergDitherer.INSTANCE
        override val disposalMethod: DisposalMethod = DisposalMethod.UNSPECIFIED
    }

//    private val opencv: PixivUgoiraEncoder = object : PixivGifEncoder() {
//        override val dir: File get() = work
//        override val quantizer: ColorQuantizer = MedianCutQuantizer.INSTANCE
//        override val ditherer: Ditherer = FloydSteinbergDitherer.INSTANCE
//        override val disposalMethod: DisposalMethod = DisposalMethod.UNSPECIFIED
//
//        @Suppress("BlockingMethodInNonBlockingContext")
//        override suspend fun encode(illust: IllustInfo, metadata: UgoiraMetadata, loop: Int): File {
//            val gif = dir.resolve("${illust.pid}.gif")
//            val output = gif.outputStream().buffered(1 shl 20)
//            val encoder = GifEncoder(output, illust.width, illust.height, loop)
//            val maxColorCount = 256
//
//            metadata.frame { frame, image ->
//                val origin = Java2DFrameUtils.toMat(image).reshape(0, image.width * image.height)
//                val src = GpuMat().apply { origin.convertTo(this, CV_32F, 1.0 / 255.0, 0.0) }
//                val labels = GpuMat()
//                val criteria = TermCriteria(TermCriteria.EPS, 3, 1.0)
//                // TermCriteria(TermCriteria.EPS, 3, 1.0)
//                val centers = GpuMat()
//
//                kmeans(
//                    src,
//                    maxColorCount,
//                    labels,
//                    criteria,
//                    1,
//                    KMEANS_PP_CENTERS,
//                    centers
//                )
//                // println(labels.type())
//                val colors = mutableSetOf<Color>()
//                val temp = Mat().apply { centers.convertTo(this, CV_64F) }
//                val buffer = temp.createBuffer<DoubleBuffer>()
//                for (x in 0 until centers.rows()) {
//                    val r = buffer[x * 3 + 0]
//                    val g = buffer[x * 3 + 1]
//                    val b = buffer[x * 3 + 2]
//                    colors.add(Color(r, g, b))
//                }
//
//                val dest = ditherer.dither(Image.fromRgb(image.readRGBs()), colors)
//                encoder.addImage(dest, frame.toImageOptions())
//            }
//
//            encoder.finishEncoding()
//            output.close()
//            return gif
//        }
//    }

    @Test
    fun gif(): Unit = runBlocking {
        val illust = PixivJson.decodeFromString<IllustInfo>(work.resolve("80722831.json").readText())
        val ugoira = PixivJson.decodeFromString<UgoiraMetadata>(work.resolve("80722831.ugoira.json").readText())
        val (file, duration) = measureTimedValue {
            gif.encode(illust = illust, metadata = ugoira)
        }
        assertTrue(file.exists())
        println("gif")
        println("${file.length() shr 20}MB")
        println(duration)
    }

//    @Test
//    fun opencv(): Unit = runBlocking {
//        val illust = PixivJson.decodeFromString<IllustInfo>(work.resolve("87036967.json").readText())
//        val ugoira = PixivJson.decodeFromString<UgoiraMetadata>(work.resolve("87036967.ugoira.json").readText())
//        val (file, duration) = measureTimedValue {
//            opencv.encode(illust = illust, metadata = ugoira)
//        }
//        assertTrue(file.exists())
//        println("gif")
//        println("${file.length() shr 20}MB")
//        println(duration)
//    }
}
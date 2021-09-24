package xyz.cssxsh.pixiv.tool

import com.squareup.gifencoder.*
import org.opencv.core.*

class OpenCVQuantizer : ColorQuantizer {
    init {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    companion object {
        @JvmStatic
        val INSTANCE = OpenCVQuantizer()
    }

    private val red = Color::class.java.getDeclaredField("red").apply { isAccessible = true }
    private val green = Color::class.java.getDeclaredField("green").apply { isAccessible = true }
    private val blue = Color::class.java.getDeclaredField("blue").apply { isAccessible = true }

    override fun quantize(originalColors: Multiset<Color>, maxColorCount: Int): MutableSet<Color> {
        val original = Mat(originalColors.size, 1, CvType.CV_64FC3).apply {
            // val buffer = createBuffer<DoubleBuffer>()
            for ((row, color) in originalColors.withIndex()) {
                put(
                    row, 0,
                    red.get(color) as Double,
                    green.get(color) as Double,
                    blue.get(color) as Double
                )
            }
        }
        val src = Mat().apply { original.convertTo(this, CvType.CV_32F) }

        val labels = Mat()
        val criteria = TermCriteria(TermCriteria.EPS or TermCriteria.MAX_ITER, 3, 1.0)
        val centers = Mat()

        Core.kmeans(
            src,
            maxColorCount,
            labels,
            criteria,
            1,
            Core.KMEANS_PP_CENTERS,
            centers
        )

        val colors = mutableSetOf<Color>()
        val temp = Mat().apply { centers.convertTo(this, CvType.CV_64F) }
        for (x in 0 until centers.rows()) {
            val r = temp[x, 0][0]
            val g = temp[x, 1][0]
            val b = temp[x, 2][0]
            colors.add(Color(r, g, b))
        }

        return colors
    }
}
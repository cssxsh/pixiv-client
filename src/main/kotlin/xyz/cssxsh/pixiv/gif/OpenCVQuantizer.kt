package xyz.cssxsh.pixiv.gif

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.TermCriteria

object OpenCVQuantizer {
    init {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    const val MAX_COUNT = "xyz.cssxsh.pixiv.tool.quantizer.max_count"

    private var last: Mat? = null

    fun reset() {
        last = null
    }

    fun quantize(originalColors: IntArray, maxColorCount: Int): IntArray {
        val original = Mat(originalColors.size, 1, CvType.CV_8UC3).apply {
            // val buffer = createBuffer<DoubleBuffer>()
            for ((row, color) in originalColors.withIndex()) {
                put(row, 0, color.asRGBBytes())
            }
        }
        val src = Mat().apply { original.convertTo(this, CvType.CV_32F, 1 / 255.0) }

        val flags =
            if (last == null) Core.KMEANS_PP_CENTERS else Core.KMEANS_USE_INITIAL_LABELS + Core.KMEANS_PP_CENTERS
        val labels = last ?: Mat()
        val criteria = TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER, 8, 1.0 / 64.0)
        val centers = Mat()

        Core.kmeans(
            src,
            maxColorCount,
            labels,
            criteria,
            1,
            flags,
            centers
        )

        last = labels

        val colors = IntArray(256)
        val temp = Mat().apply { centers.convertTo(this, CvType.CV_8UC3, 255.0) }
        for (x in 0 until centers.rows()) {
            val r = temp[x, 0][0].toInt() shl 16
            val g = temp[x, 1][0].toInt() shl 8
            val b = temp[x, 2][0].toInt()
            colors[x] = r or g or b
        }

        return colors
    }
}
package xyz.cssxsh.pixiv.tool

import com.squareup.gifencoder.*
import org.opencv.core.*

public class OpenCVQuantizer private constructor() : ColorQuantizer {
    init {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    }

    public companion object {
        @JvmField
        public val INSTANCE: OpenCVQuantizer = OpenCVQuantizer()

        internal const val MAX_COUNT = "xyz.cssxsh.pixiv.tool.quantizer.max_count"
    }

    private var last: Mat? = null

    public fun reset() {
        last = null
    }

    private val maxCount by lazy {
        System.getProperty(MAX_COUNT, "32").toInt()
    }

    override fun quantize(originalColors: Multiset<Color>, maxColorCount: Int): MutableSet<Color> {
        val original = Mat(originalColors.size, 1, CvType.CV_64FC3).apply {
            // val buffer = createBuffer<DoubleBuffer>()
            for ((row, color) in originalColors.withIndex()) {
                put(
                    row, 0,
                    color.getComponent(0),
                    color.getComponent(1),
                    color.getComponent(2)
                )
            }
        }
        val src = Mat().apply { original.convertTo(this, CvType.CV_32F) }

        val flags =
            if (last == null) Core.KMEANS_PP_CENTERS else Core.KMEANS_USE_INITIAL_LABELS + Core.KMEANS_PP_CENTERS
        val labels = last ?: Mat()
        val criteria = TermCriteria(TermCriteria.EPS + TermCriteria.MAX_ITER, maxCount, 1.0 / 64.0)
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
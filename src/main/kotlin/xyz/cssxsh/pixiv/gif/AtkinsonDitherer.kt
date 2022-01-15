package xyz.cssxsh.pixiv.gif

import java.awt.image.BufferedImage

object AtkinsonDitherer {
    private val DISTRIBUTION: List<ErrorComponent> = listOf(
        ErrorComponent(1, 0, 1 / 8.0),
        ErrorComponent(2, 0, 1 / 8.0),

        ErrorComponent(-1, 1, 1 / 8.0),
        ErrorComponent(0, 1, 1 / 8.0),
        ErrorComponent(1, 1, 1 / 8.0),

        ErrorComponent(0, 2, 1 / 8.0),
    )

    data class ErrorComponent(
        val deltaX: Int,
        val deltaY: Int,
        val power: Double
    )

    data class Color(val red: Int, val green: Int, val blue: Int) {
        constructor(rgb: Int) : this(
            red = rgb and 0xFF0000 shr 16,
            green = rgb and 0x00FF00 shr 8,
            blue = rgb and 0x0000FF
        )
    }

    operator fun Color.minus(other: Color) =
        Color(this.red - other.red, this.green - other.green, this.blue - other.blue)

    operator fun Color.plus(other: Color) =
        Color(this.red + other.red, this.green + other.green, this.blue + other.blue)

    operator fun Color.times(power: Double) =
        Color((this.red * power).toInt(), (this.green * power).toInt(), (this.blue * power).toInt())

    fun Color.nearest() = red * red + green * green + blue * blue

    fun dither(image: BufferedImage, new: IntArray): IntArray {
        val width = image.width
        val height = image.height
        val colors = Array(height) { y ->
            Array(width) { x ->
                Color(rgb = image.getRGB(x, y))
            }
        }
        val newColors = new.map { Color(it) }

        for (y in 0 until height) {
            for (x in 0 until width) {
                val original = colors[y][x]
                val replacement = newColors.minByOrNull { (it - original).nearest() }!!
                colors[y][x] = replacement
                val error = original.minus(replacement)
                for (component in DISTRIBUTION) {
                    8 * 5
                    val siblingX = x + component.deltaX
                    val siblingY = y + component.deltaY
                    if (siblingX in 0 until width && siblingY in 0 until height) {
                        val offset = error * component.power
                        colors[siblingY][siblingX] = colors[siblingY][siblingX].plus(offset)
                    }
                }
            }
        }

        return colors.flatten().map {
            var rgb = 0

            rgb = (rgb or it.red) shl 8
            rgb = (rgb or it.green) shl 8
            rgb = (rgb or it.blue)

            rgb
        }.toIntArray()
    }
}
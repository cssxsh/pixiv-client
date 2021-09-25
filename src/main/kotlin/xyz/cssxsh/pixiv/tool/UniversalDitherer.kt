package xyz.cssxsh.pixiv.tool

import com.squareup.gifencoder.*

open class UniversalDitherer(private val distribution: List<ErrorComponent>) : Ditherer {
    override fun dither(image: Image, newColors: MutableSet<Color>): Image {
        val width = image.width
        val height = image.height
        val colors = Array(height) { y -> Array(width) { x -> image.getColor(x, y) } }

        for (y in 0 until height) {
            for (x in 0 until width) {
                val original = colors[y][x]
                val replacement = original.getNearestColor(newColors)
                colors[y][x] = replacement
                val error = original.minus(replacement)
                for (component in distribution) {
                    val siblingX = x + component.deltaX
                    val siblingY = y + component.deltaY
                    if (siblingX in 0 until width && siblingY in 0 until height) {
                        val offset = error.scaled(component.power)
                        colors[siblingY][siblingX] = colors[siblingY][siblingX].plus(offset)
                    }
                }
            }
        }

        return Image.fromColors(colors)
    }

    data class ErrorComponent(
        val deltaX: Int,
        val deltaY: Int,
        val power: Double
    )
}
package xyz.cssxsh.pixiv.tool

class SierraLiteDitherer private constructor() : UniversalDitherer(DISTRIBUTION) {
    companion object {
        @JvmField
        val DISTRIBUTION: List<ErrorComponent> = listOf(
            ErrorComponent(1, 0, 2 / 4.0),

            ErrorComponent(-1, 1, 1 / 4.0),
            ErrorComponent(0, 1, 1 / 4.0),
        )

        @JvmField
        val INSTANCE: SierraLiteDitherer = SierraLiteDitherer()
    }
}
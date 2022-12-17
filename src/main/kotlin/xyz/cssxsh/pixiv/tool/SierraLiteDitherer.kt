package xyz.cssxsh.pixiv.tool

public class SierraLiteDitherer private constructor() : UniversalDitherer(DISTRIBUTION) {
    public companion object {
        @JvmStatic
        public val DISTRIBUTION: List<ErrorComponent> = listOf(
            ErrorComponent(1, 0, 2 / 4.0),

            ErrorComponent(-1, 1, 1 / 4.0),
            ErrorComponent(0, 1, 1 / 4.0),
        )

        @JvmStatic
        public val INSTANCE: SierraLiteDitherer = SierraLiteDitherer()
    }
}
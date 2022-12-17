package xyz.cssxsh.pixiv.tool

public class AtkinsonDitherer private constructor() : UniversalDitherer(DISTRIBUTION) {
    public companion object {
        @JvmStatic
        public val DISTRIBUTION: List<ErrorComponent> = listOf(
            ErrorComponent(1, 0, 1 / 8.0),
            ErrorComponent(2, 0, 1 / 8.0),

            ErrorComponent(-1, 1, 1 / 8.0),
            ErrorComponent(0, 1, 1 / 8.0),
            ErrorComponent(1, 1, 1 / 8.0),

            ErrorComponent(0, 2, 1 / 8.0),
        )

        @JvmStatic
        public val INSTANCE: AtkinsonDitherer = AtkinsonDitherer()
    }
}
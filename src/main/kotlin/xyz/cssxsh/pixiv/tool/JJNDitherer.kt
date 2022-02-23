package xyz.cssxsh.pixiv.tool

public class JJNDitherer private constructor() : UniversalDitherer(DISTRIBUTION) {
    public companion object {
        @JvmField
        public val DISTRIBUTION: List<ErrorComponent> = listOf(
            ErrorComponent(1, 0, 7 / 48.0),
            ErrorComponent(2, 0, 5 / 48.0),

            ErrorComponent(-2, 1, 3 / 48.0),
            ErrorComponent(-1, 1, 5 / 48.0),
            ErrorComponent(0, 1, 7 / 48.0),
            ErrorComponent(1, 1, 5 / 48.0),
            ErrorComponent(2, 1, 3 / 48.0),

            ErrorComponent(-2, 2, 1 / 48.0),
            ErrorComponent(-1, 2, 3 / 48.0),
            ErrorComponent(0, 2, 5 / 48.0),
            ErrorComponent(1, 2, 3 / 48.0),
            ErrorComponent(2, 2, 1 / 48.0),
        )

        @JvmField
        public val INSTANCE: JJNDitherer = JJNDitherer()
    }
}
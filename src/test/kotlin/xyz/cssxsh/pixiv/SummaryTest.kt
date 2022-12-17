package xyz.cssxsh.pixiv

import org.junit.jupiter.api.*
import java.io.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal abstract class SummaryTest {

    private var stdout: PrintStream? = null

    @BeforeAll
    open fun init() {
        val markdown = File(System.getenv("GITHUB_STEP_SUMMARY") ?: "run/summary.md")
        if (markdown.exists()) {
            stdout = System.out
            System.setOut(PrintStream(markdown))
        }
    }

    @AfterAll
    open fun redirect() {
        if (stdout != null) {
            System.setOut(stdout)
        }
    }
}
package xyz.cssxsh.pixiv

import java.io.File
import java.io.PrintStream
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

internal abstract class SummaryTest {

    private var stdout: PrintStream? = null

    @BeforeTest
    open fun init() {
        val markdown = File(System.getenv("GITHUB_STEP_SUMMARY") ?: "run/summary.md")
        if (markdown.exists()) {
            stdout = System.out
            System.setOut(PrintStream(markdown))
        }
    }

    @AfterTest
    open fun redirect() {
        if (stdout != null) {
            System.setOut(stdout)
        }
    }
}
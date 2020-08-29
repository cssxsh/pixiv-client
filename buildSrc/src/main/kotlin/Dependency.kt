

fun kotlinx(module: String, version: String? = null) =
    "org.jetbrains.kotlinx:kotlinx-$module${version?.let { ":$it" } ?:""}"

fun ktor(module: String, version: String? = null) =
    "io.ktor:ktor-$module${version?.let { ":$it" } ?:""}"

fun korlibs(module: String, version: String? = null) =
    "com.soywiz.korlibs.$module:$module${version?.let { ":$it" } ?:""}"
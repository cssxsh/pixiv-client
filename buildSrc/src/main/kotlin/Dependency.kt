

fun kotlinx(simpleModuleName: String, version: String? = null) =
    "org.jetbrains.kotlinx:kotlinx-$simpleModuleName${version?.let { ":$it" } ?:""}"

fun ktor(simpleModuleName: String, version: String? = null) =
    "io.ktor:ktor-$simpleModuleName${version?.let { ":$it" } ?:""}"

fun korlibs(simpleModuleName: String, version: String? = null) =
    "com.soywiz.korlibs.$simpleModuleName:$simpleModuleName${version?.let { ":$it" } ?:""}"

fun okhttp3(simpleModuleName: String, version: String? = null) =
    "com.squareup.okhttp3:$simpleModuleName${version?.let { ":$it" } ?:""}"
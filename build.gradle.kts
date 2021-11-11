import java.util.*

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "xzy.cssxsh.pixiv"
version = "1.0.0"

repositories {
    mavenLocal()
    if (Locale.getDefault() == Locale.SIMPLIFIED_CHINESE) {
        maven(url = "https://maven.aliyun.com/repository/central")
        maven(url = "https://maven.aliyun.com/repository/gradle-plugin")
    }
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(kotlinx("coroutines-core", Versions.coroutines))
    implementation(ktor("client-auth", "1.6.1")) {
        exclude(group = "io.ktor")
    }
    implementation(ktor("client-core", Versions.ktor))
    implementation(ktor("client-serialization", Versions.ktor))
    implementation(ktor("client-encoding", Versions.ktor))
    implementation(ktor("client-okhttp", Versions.ktor))
    implementation(okhttp3("okhttp", Versions.okhttp))
    implementation(okhttp3("okhttp-dnsoverhttps", Versions.okhttp))
    api(square("gifencoder", Versions.gifencoder))
    compileOnly(fileTree(File(requireNotNull(System.getenv("OPENCV_HOME")) {
        "请安装 OPENCV 并设置 环境变量 OPENCV_HOME 和 PATH "
    }).resolve("build/java")))
    //testImplementation("org.bytedeco.javacv:demo:1.5.6")
    //testImplementation("org.bytedeco:javacv-platform:1.5.6")
    //testImplementation("org.bytedeco:opencv-platform-gpu:4.5.3-1.5.6")
    // implementation(jsoup(Versions.jsoup))
    testImplementation(kotlin("test-junit5"))
    testImplementation(junit("api", Versions.junit))
    testRuntimeOnly(junit("engine", Versions.junit))
    testRuntimeOnly(fileTree(File(System.getenv("OPENCV_HOME")).resolve("build/java")))
}

kotlin {
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
}

tasks {

    test {
        useJUnitPlatform()
    }

    compileKotlin {
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
        kotlinOptions.jvmTarget = "11"
    }

    compileTestKotlin {
        kotlinOptions.freeCompilerArgs += "-Xjvm-default=all"
        kotlinOptions.jvmTarget = "11"
    }
}
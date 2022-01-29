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
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")
    api("io.ktor:ktor-client-auth:1.6.5")
    api("io.ktor:ktor-client-serialization:1.6.5") {
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-json")
    }
    api("io.ktor:ktor-client-encoding:1.6.5")
    api("io.ktor:ktor-client-okhttp:1.6.5")
    api("io.ktor:ktor-network:1.6.5")
    api("com.squareup.okhttp3:okhttp:4.9.2")
    api("com.squareup.okhttp3:okhttp-dnsoverhttps:4.9.2")
    api("com.squareup:gifencoder:0.10.1")
    /**
     * $OPENCV_HOME = ...
     * $PATH = $PATH;OPENCV_HOME/build/bin;OPENCV_HOME/build/java/x64
     */
    val opencv: String? = System.getenv("OPENCV_HOME")
    if (opencv != null) {
        compileOnly(fileTree(File(opencv).resolve("build/java")))
    } else {
        compileOnly("org.openpnp:opencv:4.5.1-2")
    }
    compileOnly("org.seleniumhq.selenium:selenium-java:4.1.1")

    testImplementation(kotlin("test", kotlin.coreLibrariesVersion))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.1.1")
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

    compileJava {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    compileTestJava {
        sourceCompatibility = "11"
        targetCompatibility = "11"
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
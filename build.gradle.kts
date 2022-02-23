plugins {
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.serialization") version "1.6.0"
    id("net.mamoe.maven-central-publish") version "0.7.1"
}

group = "xyz.cssxsh.pixiv"
version = "1.0.0"

mavenCentralPublish {
    useCentralS01()
    singleDevGithubProject("cssxsh", "pixiv-client", "cssxsh")
    licenseFromGitHubProject("AGPL-3.0", "master")
}

repositories {
    mavenLocal()
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
    compileOnly("org.seleniumhq.selenium:selenium-java:4.1.2")

    testImplementation(kotlin("test", "1.6.0"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.1.2")
}

kotlin {
    explicitApi()
    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }
    target.compilations {
        all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    test {
        useJUnitPlatform()
    }
}
@file:Suppress("OPT_IN_USAGE")

import java.util.*

plugins {
    kotlin("multiplatform") version "1.9.10"
    kotlin("plugin.serialization") version "1.9.10"
    id("com.github.gmazzo.buildconfig") version "3.1.0"
}

group = "xyz.cssxsh.pixiv"
version = "1.3.1"

repositories {
    mavenLocal()
    mavenCentral()
}

kotlin {
    explicitApi()
    jvmToolchain(17)
    targetHierarchy.default()
    jvm()
    macosX64()
    linuxX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(platform("io.ktor:ktor-bom:2.3.3"))
                api("io.ktor:ktor-client-auth")
                api("io.ktor:ktor-client-encoding")
                api("io.ktor:ktor-client-cio")
                api("io.ktor:ktor-client-content-negotiation")
                api("io.ktor:ktor-serialization-kotlinx-json")
//                api(platform("com.squareup.okhttp3:okhttp-bom:4.11.0"))
                implementation("com.squareup.okio:okio:3.5.0")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

                implementation(platform("org.kotlincrypto.hash:bom:0.3.0"))
                implementation("org.kotlincrypto.hash:sha2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            }
        }
        commonTest.configure {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp")
            }
        }

        val nativeMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-curl")
            }
        }
    }
}

val properties = Properties().apply {
    try {
        load(rootProject.file("local.properties").reader())
    } catch (e: Exception) {
        println("local.properties file not found, you won't be able to run tests")
    }
}

buildConfig {
    buildConfigField("String", "REFRESH_TOKEN", provider { "\"${properties["refresh_token"]}\"" })
}
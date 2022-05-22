plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"
    id("net.mamoe.maven-central-publish") version "0.7.1"
}

group = "xyz.cssxsh.pixiv"
version = "1.1.0"

mavenCentralPublish {
    useCentralS01()
    singleDevGithubProject("cssxsh", "pixiv-client", "cssxsh")
    licenseFromGitHubProject("AGPL-3.0", "master")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    api("io.ktor:ktor-client-auth:1.6.7")
    api("io.ktor:ktor-client-serialization:1.6.7") {
        exclude("org.jetbrains.kotlinx", "kotlinx-serialization-json")
    }
    api("io.ktor:ktor-client-encoding:1.6.7")
    api("io.ktor:ktor-client-okhttp:1.6.7")
    api("com.squareup.okhttp3:okhttp-dnsoverhttps:4.9.2")
    api("com.squareup:gifencoder:0.10.1")
    compileOnly("org.openpnp:opencv:4.5.1-2")
    compileOnly("org.seleniumhq.selenium:selenium-java:4.1.4")

    testImplementation(kotlin("test", "1.6.21"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.1.4")
}

kotlin {
    explicitApi()
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
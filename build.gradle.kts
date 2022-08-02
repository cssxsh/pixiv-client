plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("me.him188.maven-central-publish") version "1.0.0-dev-3"
}

group = "xyz.cssxsh.pixiv"
version = "1.2.5"

mavenCentralPublish {
    useCentralS01()
    singleDevGithubProject("cssxsh", "pixiv-client", "cssxsh")
    licenseFromGitHubProject("AGPL-3.0", "master")
    workingDir = System.getenv("PUBLICATION_TEMP")?.let { file(it).resolve(projectName) }
        ?: project.buildDir.resolve("publishing-tmp")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    api("io.ktor:ktor-client-auth:2.0.3")
    api("io.ktor:ktor-client-encoding:2.0.3")
    api("io.ktor:ktor-client-okhttp:2.0.3")
    api("io.ktor:ktor-client-content-negotiation:2.0.3")
    api("io.ktor:ktor-serialization-kotlinx-json:2.0.3")
    api("com.squareup.okhttp3:okhttp:4.10.0")
    api("com.squareup.okhttp3:okhttp-dnsoverhttps:4.10.0")
    api("com.squareup:gifencoder:0.10.1")
    compileOnly("org.openpnp:opencv:4.5.1-2")
    compileOnly("org.seleniumhq.selenium:selenium-java:4.3.0")

    testImplementation(kotlin("test", "1.7.0"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.3.0")
}

kotlin {
    explicitApi()
    target.compilations {
        all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    test {
        useJUnitPlatform()
    }
}